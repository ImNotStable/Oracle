package com.imnotstable.oracle.commands;

import com.imnotstable.oracle.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReplyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            if (MessageCommand.reply.containsKey(sender)) {
                CommandSender receiver = MessageCommand.reply.get(sender);
                String message = String.join("", args);
                sender.sendMessage(Component.text(ColorUtils.colored("&3To " + receiver.getName() + ": &7") + message));
                receiver.sendMessage(Component.text(ColorUtils.colored("&3From " + sender.getName() + ": &7") + message));
            } else {
                sender.sendMessage(ColorUtils.coloredComponent("&cYou have no one to reply to."));
            }
        }
        return false;
    }
}
