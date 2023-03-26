package com.imnotstable.oracle.commands;

import com.imnotstable.oracle.Oracle;
import com.imnotstable.oracle.utils.ColorUtils;
import com.imnotstable.oracle.utils.GuiUtils;
import com.imnotstable.oracle.utils.PlayerDataUtils;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.List;

public class ChatColorCommand implements CommandExecutor, Listener {

    private static final HashMap<UUID, String> chatcolor = new HashMap<>();

    public static String getChatColor(UUID uuid) {
        return chatcolor.getOrDefault(uuid, "&7");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            Inventory gui = Bukkit.createInventory(null, 54, ColorUtils.coloredComponent("&bChatcolor"));
            ItemStack backgroundItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta backgroundMeta = backgroundItem.getItemMeta();
            backgroundMeta.displayName(ColorUtils.coloredComponent(("&8")));
            backgroundItem.setItemMeta(backgroundMeta);
            for (int i = 0; i < 54; i++) {
                gui.setItem(i, backgroundItem);
            }
            gui.setItem(10, GuiUtils.easyItemizer(new ItemStack(Material.WHITE_DYE), "&fWhite"));
            gui.setItem(11, GuiUtils.easyItemizer(new ItemStack(Material.LIGHT_GRAY_DYE), "&7Light Gray"));
            gui.setItem(12, GuiUtils.easyItemizer(new ItemStack(Material.GRAY_DYE), "&8Gray"));
            gui.setItem(13, GuiUtils.easyItemizer(new ItemStack(Material.BLACK_DYE), "&0Black"));
            gui.setItem(14, GuiUtils.easyItemizer(new ItemStack(Material.RED_DYE), "&cRed"));
            gui.setItem(15, GuiUtils.easyItemizer(new ItemStack(Material.ORANGE_DYE), "&6Orange"));
            gui.setItem(16, GuiUtils.easyItemizer(new ItemStack(Material.YELLOW_DYE), "&eYellow"));
            gui.setItem(19, GuiUtils.easyItemizer(new ItemStack(Material.LIME_DYE), "&aLime"));
            gui.setItem(20, GuiUtils.easyItemizer(new ItemStack(Material.GREEN_DYE), "&2Green"));
            gui.setItem(21, GuiUtils.easyItemizer(new ItemStack(Material.CYAN_DYE), "&3Cyan"));
            gui.setItem(22, GuiUtils.easyItemizer(new ItemStack(Material.LIGHT_BLUE_DYE), "&bLight Blue"));
            gui.setItem(23, GuiUtils.easyItemizer(new ItemStack(Material.BLUE_DYE), "&9Blue"));
            gui.setItem(24, GuiUtils.easyItemizer(new ItemStack(Material.PURPLE_DYE), "&5Purple"));
            gui.setItem(25, GuiUtils.easyItemizer(new ItemStack(Material.PINK_DYE), "&dPink"));
            miscellaneousItems(gui, player);
            player.openInventory(gui);
        }
        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().title().equals(ColorUtils.coloredComponent("&bChatcolor"))) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType().equals(Material.GRAY_STAINED_GLASS_PANE) || clickedItem.getType().equals(Material.BOOK)) return;
            String color = chatcolor.getOrDefault(player.getUniqueId(), "&7");
            Inventory gui = event.getInventory();
            String code;
            if (clickedItem.getType().equals(Material.NAME_TAG)) {
                code = PlainTextComponentSerializer.plainText().serialize(Objects.requireNonNull(clickedItem.getItemMeta().displayName())).substring(2, 4);
                if (color.contains(code)) {
                    color = color.replaceAll(code, "");
                } else {
                    color = color + code;
                }
            } else {
                code = PlainTextComponentSerializer.plainText().serialize(Objects.requireNonNull(clickedItem.getItemMeta().displayName())).substring(0, 2);
                color = color.replaceFirst(color.substring(0, 2), code);
            }
            chatcolor.put(player.getUniqueId(), color);
            PlayerDataUtils.setString(player.getUniqueId(), "chatcolor", color);
            miscellaneousItems(gui, player);
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        String color = PlayerDataUtils.getString(uuid, "chatcolor");
        chatcolor.put(uuid, color == null ? "&7" : color);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        chatcolor.remove(event.getPlayer().getUniqueId());
    }

    public void miscellaneousItems(Inventory gui, Player player){
        String color = chatcolor.getOrDefault(player.getUniqueId(), "&7").substring(0, 2);
        gui.setItem(38, GuiUtils.easyItemizer(new ItemStack(Material.NAME_TAG), color + "&lBold", new ArrayList<>(List.of(color + "Add boldness to your chat!"))));
        gui.setItem(39, GuiUtils.easyItemizer(new ItemStack(Material.NAME_TAG), color + "&mStrikethrough", new ArrayList<>(List.of(color + "Add lines through your chat!"))));
        gui.setItem(40, GuiUtils.easyItemizer(new ItemStack(Material.BOOK), "&3Here's what your chat will look like!",
                new ArrayList<>(List.of(Oracle.getChat().getPlayerPrefix(player) + player.getName() + Oracle.getChat().getPlayerSuffix(player) + " &8» " + chatcolor.getOrDefault(player.getUniqueId(), "&7") + "This is a preview"))));
        gui.setItem(41, GuiUtils.easyItemizer(new ItemStack(Material.NAME_TAG), color + "&nUnderline", new ArrayList<>(List.of(color + "Add lines under your chat!"))));
        gui.setItem(42, GuiUtils.easyItemizer(new ItemStack(Material.NAME_TAG), color + "&oItalic", new ArrayList<>(List.of(color + "Add italics to your chat!"))));
    }
}
