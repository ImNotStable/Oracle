package com.imnotstable.oracle.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class BanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (target.isOnline()) {
                Player kickPlayer = target.getPlayer();
                if (kickPlayer != null)
                    kickPlayer.kick();
            }
            String message = String.join(" ", Arrays.asList(args).subList(1, args.length));
            target.banPlayer(message);
        }
        return true;
    }
}
