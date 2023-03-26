package com.imnotstable.oracle.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GuiUtils {

    public static ItemStack easyItemizer(final ItemStack item, final String name, final ArrayList<String> lore, final int customData) {
        ItemMeta meta = item.getItemMeta();
        meta.displayName(ColorUtils.coloredComponent(name));
        meta.lore(lore.stream().map(ColorUtils::coloredComponent).collect(Collectors.toList()));
        meta.setCustomModelData(customData);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack easyItemizer(final ItemStack item, final String name, final ArrayList<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.displayName(ColorUtils.coloredComponent(name));
        if (lore.size() > 0)
            meta.lore(lore.stream().map(ColorUtils::coloredComponent).collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack easyItemizer(final ItemStack item, final String name) {
        ItemMeta meta = item.getItemMeta();
        meta.displayName(ColorUtils.coloredComponent(name));
        item.setItemMeta(meta);
        return item;
    }

}
