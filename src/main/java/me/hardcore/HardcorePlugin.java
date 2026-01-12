package me.hardcore;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class HardcorePlugin extends JavaPlugin {

    private static HardcorePlugin instance;
    private File dataFile;
    private FileConfiguration data;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadData();

        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getCommand("hoisinh").setExecutor(new ReviveCommand());
        getCommand("hardcore").setExecutor(new ReviveCommand());

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new HardcorePlaceholder().register();
        }
    }

    @Override
    public void onDisable() {
        saveData();
    }

    public static HardcorePlugin getInstance() {
        return instance;
    }

    public void loadData() {
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }
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
        return data.getInt("players." + uuid, 5);
    }

    public void setLives(UUID uuid, int lives) {
        data.set("players." + uuid, lives);
        saveData();
    }

    public Map<String, Object> getReviveCost() {
        return getConfig().getConfigurationSection("revive-cost").getValues(false);
    }

    public boolean isEffectEnabled(String key) {
        return getConfig().getBoolean("revive." + key, true);
    }
}
