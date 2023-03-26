package com.imnotstable.oracle.economy;

import com.imnotstable.oracle.utils.ColorUtils;
import com.imnotstable.oracle.utils.NumberUtils;
import com.imnotstable.oracle.utils.PlayerDataUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EconomyCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) { //eco player string int
        if (args.length > 1) {
            OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(args[0]);
            if (player == null) {
                sender.sendMessage(ColorUtils.colored("&cPlayer not found."));
                return true;
            }
            double num = 0;
            if (!args[1].equals("reset")) {
                if (args.length < 3) {
                    sender.sendMessage(ColorUtils.colored("&cInvalid usage."));
                    return true;
                }
                try {
                    num = Double.parseDouble(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ColorUtils.colored("&cInvalid number."));
                }
            }
            UUID uuid = player.getUniqueId();
            switch (args[1]) {
                case "reset" -> {
                    PlayerDataUtils.setDouble(uuid, "balance", 0D);
                    sender.sendMessage(ColorUtils.colored("&7Reset &b" + player.getName() + "'s&7 balance"));
                }
                case "set" -> {
                    PlayerDataUtils.setDouble(uuid, "balance", num);
                    sender.sendMessage(ColorUtils.colored("&7Set &b" + player.getName() + "'s&7 balance to &b$" + NumberUtils.formatCommas(num)));
                }
                case "remove" -> {
                    double balance = PlayerDataUtils.getDouble(uuid, "balance");
                    balance -= num;
                    PlayerDataUtils.setDouble(uuid, "balance", balance);
                    sender.sendMessage(ColorUtils.colored("&b" + player.getName() + "'s&7 balance is now &b$" + NumberUtils.formatCommas(balance)));
                }
                case "add" -> {
                    double balance = PlayerDataUtils.getDouble(uuid, "balance");
                    balance += num;
                    PlayerDataUtils.setDouble(uuid, "balance", balance);
                    sender.sendMessage(ColorUtils.colored("&b" + player.getName() + "'s&7 balance is now &b$" + NumberUtils.formatCommas(balance)));
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        } else if (args.length == 2) {
            completions.add("reset");
            completions.add("set");
            completions.add("remove");
            completions.add("add");
        } else if (args.length == 3) {
            if (args[1].equals("set") || args[1].equals("remove") || args[1].equals("add")) {
                completions.add("<amount>");
            }
        }
        return StringUtil.copyPartialMatches(args[args.length - 1], completions, new ArrayList<>());
    }
}
