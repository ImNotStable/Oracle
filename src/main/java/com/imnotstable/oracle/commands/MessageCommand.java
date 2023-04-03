package com.imnotstable.oracle.commands;

import com.imnotstable.oracle.commands.staff.VanishCommand;
import com.imnotstable.oracle.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;

public class MessageCommand implements CommandExecutor {
    public static final HashMap<Player, Player> reply = new HashMap<>();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 1) {
            Player receiver = Bukkit.getPlayerExact(args[0]);
            if (receiver == null || (VanishCommand.isVanished(receiver) && !sender.hasPermission("command.vanish.bypass"))) {
                sender.sendMessage(ColorUtils.coloredComponent("&cPlayer not found."));
                return true;
            }
            String message = String.join(" ", Arrays.asList(args).subList(1, args.length));
            sender.sendMessage(Component.text(ColorUtils.colored("&3To " + receiver.getName() + ": &7") + message));
            if (sender instanceof Player player) {
                reply.put(receiver, player);
            }
            receiver.sendMessage(Component.text(ColorUtils.colored("&3From " + sender.getName() + ": &7") + message));
            if (sender instanceof Player player) {
                reply.put(player, receiver);
            }
        } else {
            sender.sendMessage(ColorUtils.coloredComponent("&cIncorrect Usage."));
        }
        return true;
    }
}
