package com.imnotstable.oracle.stafftracker;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class DataManager {
    private static final String path = "plugins/Oracle/staffdata/";
    public static final HashMap<UUID, YamlConfiguration> loadedConfigurations = new HashMap<>();

    public static File getFile(UUID uuid) {
        return new File(path + uuid + ".yml");
    }

    public static boolean fileExists(File file){
        return file.exists();
    }

    public static void saveAllConfigurations() {
        loadedConfigurations.forEach((uuid, yamlConfiguration) -> save(getFile(uuid), yamlConfiguration));
    }

    public static void loadAllConfigurations() {
        File folder = new File(path);
        if (folder.listFiles() != null) {
            Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                    .filter(file -> file.isFile() && file.getName().endsWith(".yml"))
                    .forEach(file -> {
                        if (YamlConfiguration.loadConfiguration(file).contains("stafftracker"))
                            loadedConfigurations.put(UUID.fromString(file.getName().replaceAll(".yml", "")), YamlConfiguration.loadConfiguration(file));
                    });
        }
    }

    public static void save(File file, YamlConfiguration config){
        try {
            config.save(file);
        } catch (IOException ignored) {
        }
    }

    public static void createAccount(OfflinePlayer player) {
        if (!accountExists(player)) {
            File file = getFile(player.getUniqueId());
            save(file, loadAccount(player));
        }
        loadAccount(player);
    }

    public static YamlConfiguration loadAccount(OfflinePlayer player) {
        File file = getFile(player.getUniqueId());
        loadedConfigurations.put(player.getUniqueId(), YamlConfiguration.loadConfiguration(file));
        return loadedConfigurations.get(player.getUniqueId());
    }

    public static YamlConfiguration loadAccount(UUID uuid) {
        File file = getFile(uuid);
        loadedConfigurations.put(uuid, YamlConfiguration.loadConfiguration(file));
        return loadedConfigurations.get(uuid);
    }

    public static YamlConfiguration getAccount(OfflinePlayer player) {
        if (!loadedConfigurations.containsKey(player.getUniqueId())) {
            return loadAccount(player);
        }
        return loadedConfigurations.get(player.getUniqueId());
    }

    public static YamlConfiguration getAccount(UUID uuid) {
        if (!loadedConfigurations.containsKey(uuid)) {
            return loadAccount(uuid);
        }
        return loadedConfigurations.get(uuid);
    }

    public static boolean accountExists(OfflinePlayer player) {
        return fileExists(getFile(player.getUniqueId()));
    }

    public static void setTime(OfflinePlayer player, long time) {
        YamlConfiguration config = getAccount(player);
        config.set("stafftracker", time);
    }

    public static long getTime(OfflinePlayer player) {
        YamlConfiguration config = getAccount(player);
        if (!config.contains("stafftracker"))
            config.set("stafftracker", 0L);
        return config.getLong("stafftracker");
    }

    public static long getTime(UUID uuid) {
        YamlConfiguration config = getAccount(uuid);
        if (!config.contains("stafftracker"))
            config.set("stafftracker", 0L);
        return config.getLong("stafftracker");
    }
    public static void addTime(OfflinePlayer player, long amount) {
        long newTime = getTime(player) + amount;
        setTime(player, newTime);
    }

    public static void setWeek(OfflinePlayer player, int week) {
        YamlConfiguration config = getAccount(player);
        config.set("week", week);
    }

    public static int getWeek(OfflinePlayer player) {
        YamlConfiguration config = getAccount(player);
        if (!config.contains("week"))
            config.set("week", StaffTrackerManager.getWeek());
        return config.getInt("week");
    }

}
