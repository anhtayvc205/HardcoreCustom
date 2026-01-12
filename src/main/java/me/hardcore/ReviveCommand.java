package me.hardcore;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ReviveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        HardcorePlugin plugin = HardcorePlugin.getInstance();

        if (cmd.getName().equalsIgnoreCase("hardcore")) {
            plugin.reloadConfig();
            plugin.loadData();
            sender.sendMessage("§aReload xong.");
            return true;
        }

        if (!(sender instanceof Player reviver)) return true;
        if (args.length != 1) return true;

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) return true;
        if (plugin.getLives(target.getUniqueId()) > 0) return true;

        if (!hasItems(reviver, plugin)) return true;
        removeItems(reviver, plugin);

        target.setGameMode(GameMode.SURVIVAL);
        playEffect(target, plugin);

        return true;
    }

    private boolean hasItems(Player p, HardcorePlugin plugin) {
        for (Map.Entry<String, Object> e : plugin.getReviveCost().entrySet()) {
            if (!p.getInventory().containsAtLeast(
                    new ItemStack(Material.valueOf(e.getKey())),
                    (int) e.getValue())) return false;
        }
        return true;
    }

    private void removeItems(Player p, HardcorePlugin plugin) {
        for (Map.Entry<String, Object> e : plugin.getReviveCost().entrySet()) {
            p.getInventory().removeItem(
                    new ItemStack(Material.valueOf(e.getKey()), (int) e.getValue()));
        }
    }

    private void playEffect(Player p, HardcorePlugin plugin) {
        Location l = p.getLocation();
        World w = p.getWorld();

        if (plugin.isEffectEnabled("lightning"))
            w.strikeLightningEffect(l);
        if (plugin.isEffectEnabled("particle"))
            w.spawnParticle(Particle.TOTEM_OF_UNDYING, l.add(0,1,0), 200);
        if (plugin.isEffectEnabled("sound"))
            w.playSound(l, Sound.ITEM_TOTEM_USE, 1f, 1f);
    }
}
