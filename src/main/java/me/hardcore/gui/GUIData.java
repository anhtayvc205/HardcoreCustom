package me.hardcore.gui;

import org.bukkit.entity.Player;
import java.util.*;

public class GUIData {
    private static final Map<UUID, UUID> map = new HashMap<>();

    public static void set(Player reviver, Player target) {
        map.put(reviver.getUniqueId(), target.getUniqueId());
    }

    public static Player get(Player reviver) {
        UUID id = map.get(reviver.getUniqueId());
        return id == null ? null : reviver.getServer().getPlayer(id);
    }
}
