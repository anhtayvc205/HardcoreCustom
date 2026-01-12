package me.hardcore.gui;

import me.hardcore.HardcorePlugin;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;

import java.util.Map;

public class ConfirmGUI implements Listener {

    private static final String TITLE = "§aXác nhận hồi sinh";

    public static void open(Player reviver, Player target) {
        Inventory inv = Bukkit.createInventory(null, 27, TITLE);
        HardcorePlugin plugin = HardcorePlugin.getInstance();

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        var meta = (org.bukkit.inventory.meta.SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(target);
        meta.setDisplayName("§eHồi sinh: §a" + target.getName());
        head.setItemMeta(meta);
        inv.setItem(13, head);

        int slot = 0;
        for (Map.Entry<String, Object> e : plugin.getReviveCost().entrySet()) {
            Material m = Material.valueOf(e.getKey());
            int amount = ((Number) e.getValue()).intValue();
            inv.setItem(slot++, new ItemStack(m, amount));
        }

        inv.setItem(22, btn(Material.LIME_WOOL, "§aĐỒNG Ý"));
        inv.setItem(26, btn(Material.RED_WOOL, "§cHỦY"));

        GUIData.set(reviver, target);
        reviver.openInventory(inv);
    }

    private static ItemStack btn(Material m, String name) {
        ItemStack i = new ItemStack(m);
        var meta = i.getItemMeta();
        meta.setDisplayName(name);
        i.setItemMeta(meta);
        return i;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals(TITLE)) return;
        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        Player target = GUIData.get(p);
        if (target == null) return;

        HardcorePlugin plugin = HardcorePlugin.getInstance();

        if (e.getSlot() == 22) {
            // kiểm tra nguyên liệu
            for (Map.Entry<String, Object> en : plugin.getReviveCost().entrySet()) {
                Material m = Material.valueOf(en.getKey());
                int need = ((Number) en.getValue()).intValue();
                int have = p.getInventory().all(m).values().stream().mapToInt(ItemStack::getAmount).sum();
                if (have < need) {
                    p.sendMessage("§cKhông đủ nguyên liệu.");
                    return;
                }
            }

            // trừ đồ
            for (Map.Entry<String, Object> en : plugin.getReviveCost().entrySet()) {
                p.getInventory().removeItem(new ItemStack(Material.valueOf(en.getKey()),
                        ((Number) en.getValue()).intValue()));
            }

            plugin.setLives(target.getUniqueId(), plugin.getConfig().getInt("revive-lives"));
            target.setGameMode(GameMode.SURVIVAL);
            target.teleport(target.getWorld().getSpawnLocation());

            p.sendMessage("§aHồi sinh thành công!");
            target.sendMessage("§aBạn đã được hồi sinh!");
            p.closeInventory();
        }

        if (e.getSlot() == 26) p.closeInventory();
    }
}
