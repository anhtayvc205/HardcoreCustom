package me.hardcore;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        HardcorePlugin plugin = HardcorePlugin.getInstance();
        var p = e.getEntity();

        int lives = plugin.getLives(p.getUniqueId()) - 1;
        plugin.setLives(p.getUniqueId(), lives);

        if (lives <= 0) {
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage("§cBạn đã hết mạng!");
        } else {
            p.sendMessage("§eBạn còn " + lives + " mạng.");
        }
    }
}
