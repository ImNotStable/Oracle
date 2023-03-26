package com.imnotstable.oracle.economy;

import com.imnotstable.oracle.utils.ColorUtils;
import com.imnotstable.oracle.utils.NumberUtils;
import com.imnotstable.oracle.utils.PlayerDataUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BalanceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 1) {
            OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(args[0]);
            if (player == null) {
                sender.sendMessage(ColorUtils.colored("&cPlayer not found."));
                return true;
            }
            sender.sendMessage(ColorUtils.colored("&7" + player.getName() + "'s Balance: &b$" + NumberUtils.formatCommas(PlayerDataUtils.getDouble(player.getUniqueId(), "balance"))));
        } else if (sender instanceof Player player) {
            sender.sendMessage(ColorUtils.colored("&7Balance: &b$" + NumberUtils.formatCommas(PlayerDataUtils.getDouble(player.getUniqueId(), "balance"))));
        }
        return true;
    }

}
