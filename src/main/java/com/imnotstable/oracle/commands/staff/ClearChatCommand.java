package com.imnotstable.oracle.commands.staff;

import com.imnotstable.oracle.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ClearChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> !player.hasPermission("command.clearchat.bypass"))
                .forEach(player -> {
                    for (int i = 0; i < 150; i++)
                        player.sendMessage("");
                });
        Bukkit.broadcast(ColorUtils.coloredComponent("&b&lOracle &8» &7Chat was cleared."));
        return true;
    }
}
