package me.hardcore;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        HardcorePlugin plugin = HardcorePlugin.getInstance();

        int lives = plugin.getLives(p.getUniqueId()) - 1;
        plugin.setLives(p.getUniqueId(), lives);

        if (lives <= 0) {
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage("§cBạn đã hết mạng!");
        } else {
            p.sendMessage("§eBạn còn §c" + lives + " §emạng.");
        }
    }
}
