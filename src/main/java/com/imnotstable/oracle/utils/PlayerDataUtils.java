package com.imnotstable.oracle.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDataUtils {

    private static final String path = "plugins/Oracle/playerdata/";
    public static final HashMap<UUID, YamlConfiguration> loadedConfigurations = new HashMap<>();

    public static File getFile(UUID uuid) {
        return new File(path + uuid + ".yml");
    }

    public static boolean loadConfiguration(UUID uuid) {
        if (configurationExists(uuid)) {
            loadedConfigurations.put(uuid, YamlConfiguration.loadConfiguration(getFile(uuid)));
            return false;
        } else {
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(getFile(uuid));
            configuration.set("username", Bukkit.getOfflinePlayer(uuid).getName());
            configuration.set("balance", 0D);
            configuration.set("tokens", 0L);
            configuration.set("nickname", Bukkit.getOfflinePlayer(uuid).getName());
            configuration.set("chatcolor", "&7");
            loadedConfigurations.put(uuid, configuration);
            saveConfiguration(uuid);
            return configurationExists(uuid);
        }
    }

    public static void unloadConfiguration(UUID uuid) {
        loadedConfigurations.remove(uuid);
    }

    public static boolean configurationExists(UUID uuid) {
        return getFile(uuid).exists();
    }

    public static boolean configurationIsLoaded(UUID uuid) {
        return loadedConfigurations.containsKey(uuid);
    }

    public static void saveConfiguration(UUID uuid) {
        try {
            loadedConfigurations.get(uuid).save(getFile(uuid));
        } catch (IOException ignored) {
        }
    }

    public static void saveAllConfigurations() {
        loadedConfigurations.forEach((uuid, yamlConfiguration) -> saveConfiguration(uuid));
    }

    public static UUID getUUID(OfflinePlayer player) {
        return player.getUniqueId();
    }

    public static UUID getUUID(String player) {
        return Bukkit.getOfflinePlayer(player).getUniqueId();
    }

    public static void setString(UUID uuid, String key, String value) {
        if (!configurationIsLoaded(uuid)) {
            loadConfiguration(uuid);
        }
        loadedConfigurations.get(uuid).set(key, value);
    }

    public static String getString(UUID uuid, String key) {
        if (configurationIsLoaded(uuid)) {
            return loadedConfigurations.get(uuid).getString(key);
        } else {
            loadConfiguration(uuid);
            String value = loadedConfigurations.get(uuid).getString(key);
            unloadConfiguration(uuid);
            return value;
        }
    }

    public static void setDouble(UUID uuid, String key, double value) {
        if (!configurationIsLoaded(uuid)) {
            loadConfiguration(uuid);
        }
        loadedConfigurations.get(uuid).set(key, value);
    }

    public static Double getDouble(UUID uuid, String key) {
        if (configurationIsLoaded(uuid)) {
            return loadedConfigurations.get(uuid).getDouble(key);
        } else {
            loadConfiguration(uuid);
            double value = loadedConfigurations.get(uuid).getDouble(key);
            unloadConfiguration(uuid);
            return value;
        }
    }

    public static void setLong(UUID uuid, String key, long value) {
        if (!configurationIsLoaded(uuid)) {
            loadConfiguration(uuid);
        }
        loadedConfigurations.get(uuid).set(key, value);
    }

    public static Long getLong(UUID uuid, String key) {
        if (configurationIsLoaded(uuid)) {
            return loadedConfigurations.get(uuid).getLong(key);
        } else {
            loadConfiguration(uuid);
            long value = loadedConfigurations.get(uuid).getLong(key);
            unloadConfiguration(uuid);
            return value;
        }
    }
}
