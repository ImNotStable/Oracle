package com.imnotstable.oracle.stafftracker;

import com.imnotstable.oracle.utils.ColorUtils;
import com.imnotstable.oracle.utils.GuiUtils;
import com.imnotstable.oracle.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StaffTrackerCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            Inventory gui = Bukkit.createInventory(null, 54, ColorUtils.coloredComponent("&bStaff Tracker"));
            int index = 0;
            for (UUID uuid : DataManager.loadedConfigurations.keySet()) {
                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) playerHead.getItemMeta();
                meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
                playerHead.setItemMeta(meta);
                gui.setItem(index, GuiUtils.easyItemizer(playerHead, "&b" + DataManager.loadedConfigurations.get(uuid).getString("username"), new ArrayList<>(List.of(ColorUtils.colored("&bWeekly Timer: " + TimeUtils.formatTime(DataManager.getTime(uuid)))))));
                index++;
            }
            player.openInventory(gui);
        }
        return true;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getView().title().equals(ColorUtils.coloredComponent("&bStaff Tracker")))
            event.setCancelled(true);
    }

}
