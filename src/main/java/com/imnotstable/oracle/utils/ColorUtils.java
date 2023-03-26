package com.imnotstable.oracle.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;

public class ColorUtils {

    public static Component coloredComponent(String string) {
        return Component.text(colored(string));
    }

    public static String colored(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
