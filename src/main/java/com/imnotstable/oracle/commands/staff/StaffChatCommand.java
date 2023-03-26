package com.imnotstable.oracle.commands.staff;

import com.imnotstable.oracle.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

import static com.imnotstable.oracle.Oracle.getChat;

public class StaffChatCommand implements CommandExecutor {
    public static final ArrayList<UUID> staffChat = new ArrayList<>();

    public static void sendStaffChat(String displayName, String message) {
        Bukkit.getOnlinePlayers().stream().filter(
                    player -> player.hasPermission("command.staffchat")).forEach(
                        player -> player.sendMessage(ColorUtils.coloredComponent("&3&o[SC] &b" + displayName + " &8» &f" + message)));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player player) {
                if (staffChat.contains(player.getUniqueId())) {
                    staffChat.remove(player.getUniqueId());
                    player.sendMessage(ColorUtils.coloredComponent("&b&lOracle &8» &7You've disabled staff chat."));
                } else {
                    staffChat.add(player.getUniqueId());
                    player.sendMessage(ColorUtils.coloredComponent("&b&lOracle &8» &7You've enabled staff chat."));
                }
            }
            return true;
        }
        String displayName = sender.getName();
        if (sender instanceof Player player) {
            displayName = getChat().getPlayerGroups(player)[0] + displayName;
        }
        sendStaffChat(displayName, String.join(" ", args));
        return true;
    }
}
