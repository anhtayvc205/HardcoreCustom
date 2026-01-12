package me.hardcore;

import me.hardcore.gui.ConfirmGUI;
import me.hardcore.gui.ReviveGUI;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HardcorePlugin extends JavaPlugin {

    private static HardcorePlugin instance;
    private File dataFile;
    private YamlConfiguration data;

    public static HardcorePlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        loadData();

        Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new ReviveGUI(), this);
        Bukkit.getPluginManager().registerEvents(new ConfirmGUI(), this);

        getCommand("hoisinh").setExecutor(new ReviveCommand());
        getCommand("hardcore").setExecutor(new ReviveCommand());

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new HardcorePlaceholder(this).register();
        }

        getLogger().info("HardcoreCustom enabled");
    }

    public void loadData() {
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) saveResource("data.yml", false);
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void saveData() {
        try {
            data.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLives(UUID uuid) {
        return data.getInt("players." + uuid + ".lives", getConfig().getInt("max-lives"));
    }

    public void setLives(UUID uuid, int lives) {
        data.set("players." + uuid + ".lives", lives);
        saveData();
    }

    public Map<String, Object> getReviveCost() {
        return getConfig().getConfigurationSection("revive-cost").getValues(false);
    }

    public boolean isEffect(String key) {
        return getConfig().getBoolean("effects." + key);
    }
}
