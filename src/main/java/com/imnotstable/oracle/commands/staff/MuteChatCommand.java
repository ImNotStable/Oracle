package com.imnotstable.oracle.commands.staff;

import com.imnotstable.oracle.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MuteChatCommand implements CommandExecutor {

    private static boolean chatMuted = false;

    public static boolean isChatMuted() {
        return chatMuted;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        chatMuted = !chatMuted;
        if (chatMuted) {
            Bukkit.broadcast(ColorUtils.coloredComponent("&b&lOracle &8» &7Chat was muted."));
        } else {
            Bukkit.broadcast(ColorUtils.coloredComponent("&b&lOracle &8» &7Chat was unmuted."));
        }
        return true;
    }
}
