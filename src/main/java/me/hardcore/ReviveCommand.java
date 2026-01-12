package me.hardcore;

import me.hardcore.gui.ReviveGUI;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ReviveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("hardcore")) {
            HardcorePlugin.getInstance().reloadConfig();
            sender.sendMessage("§aReload xong.");
            return true;
        }

        if (!(sender instanceof Player p)) {
            sender.sendMessage("Chỉ người chơi dùng được.");
            return true;
        }

        ReviveGUI.open(p);
        return true;
    }
}
