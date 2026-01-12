package me.hardcore;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class HardcorePlaceholder extends PlaceholderExpansion {

    private final HardcorePlugin plugin;

    public HardcorePlaceholder(HardcorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override public String getIdentifier() { return "hardcore"; }
    @Override public String getAuthor() { return "You"; }
    @Override public String getVersion() { return "1.4"; }

    @Override
    public String onPlaceholderRequest(Player p, String id) {
        if (p == null) return "";
        if (id.equalsIgnoreCase("lives"))
            return String.valueOf(plugin.getLives(p.getUniqueId()));
        return "";
    }
}
