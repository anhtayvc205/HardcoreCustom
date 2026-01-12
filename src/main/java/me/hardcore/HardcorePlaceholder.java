package me.hardcore;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class HardcorePlaceholder extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "hardcore";
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        if (p == null) return "";
        if (params.equalsIgnoreCase("lives")) {
            return String.valueOf(HardcorePlugin.getInstance().getLives(p.getUniqueId()));
        }
        return null;
    }
}
