package com.imnotstable.oracle.stafftracker;

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
        } catch (IOException ignored) {}
    }

    public static void createAccount(UUID uuid) {
        if (!accountExists(uuid)) {
            File file = getFile(uuid);
            save(file, loadAccount(uuid));
        }
        loadAccount(uuid);
    }

    public static YamlConfiguration loadAccount(UUID uuid) {
        File file = getFile(uuid);
        loadedConfigurations.put(uuid, YamlConfiguration.loadConfiguration(file));
        return loadedConfigurations.get(uuid);
    }

    public static YamlConfiguration getAccount(UUID uuid) {
        if (!loadedConfigurations.containsKey(uuid)) {
            return loadAccount(uuid);
        }
        return loadedConfigurations.get(uuid);
    }

    public static boolean accountExists(UUID uuid) {
        return fileExists(getFile(uuid));
    }

    public static void setTime(UUID uuid, long time) {
        YamlConfiguration config = getAccount(uuid);
        config.set("stafftracker", time);
    }

    public static long getTime(UUID uuid) {
        YamlConfiguration config = getAccount(uuid);
        if (!config.contains("stafftracker"))
            config.set("stafftracker", 0L);
        return config.getLong("stafftracker");
    }
    public static void addTime(UUID uuid, long amount) {
        long newTime = getTime(uuid) + amount;
        setTime(uuid, newTime);
    }

    public static void setWeek(UUID uuid, int week) {
        YamlConfiguration config = getAccount(uuid);
        config.set("week", week);
    }

    public static int getWeek(UUID uuid) {
        YamlConfiguration config = getAccount(uuid);
        if (!config.contains("week"))
            config.set("week", StaffTrackerManager.getWeek());
        return config.getInt("week");
    }

}
