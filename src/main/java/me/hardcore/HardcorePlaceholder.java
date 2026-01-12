package me.hardcore;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class HardcorePlaceholder extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "hardcore";
    }

    @Override
    public String getAuthor() {
        return "HardcoreCustom";
    }

    @Override
    public String getVersion() {
        return "1.3";
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (player == null) return "";

        if (params.equalsIgnoreCase("lives")) {
            return String.valueOf(
                HardcorePlugin.getInstance().getLives(player.getUniqueId())
            );
        }

        return null;
    }
}
