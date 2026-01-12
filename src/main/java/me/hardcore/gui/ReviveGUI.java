package me.hardcore.gui;

import me.hardcore.HardcorePlugin;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.SkullMeta;

public class ReviveGUI implements Listener {

    private static final String TITLE = "§cChọn người hồi sinh";

    public static void open(Player viewer) {
        Inventory inv = Bukkit.createInventory(null, 54, TITLE);
        HardcorePlugin plugin = HardcorePlugin.getInstance();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (plugin.getLives(p.getUniqueId()) <= 0) {
                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) head.getItemMeta();
                meta.setOwningPlayer(p);
                meta.setDisplayName("§c" + p.getName());
                head.setItemMeta(meta);
                inv.addItem(head);
            }
        }
        viewer.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals(TITLE)) return;
        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getType() != Material.PLAYER_HEAD) return;

        SkullMeta meta = (SkullMeta) e.getCurrentItem().getItemMeta();
        Player target = meta.getOwningPlayer().getPlayer();
        if (target == null) return;

        ConfirmGUI.open(p, target);
    }
}
