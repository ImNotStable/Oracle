package com.imnotstable.oracle.commands.staff;

import com.imnotstable.oracle.Oracle;
import com.imnotstable.oracle.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class VanishCommand implements CommandExecutor {

    private static final ArrayList<Player> vanishedPlayers = new ArrayList<>();

    public static ArrayList<Player> getVanishedPlayers() {
        return vanishedPlayers;
    }

    public static boolean isVanished(Player player) {
        return getVanishedPlayers().contains(player);
    }

    public VanishCommand(Oracle plugin) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> getVanishedPlayers()
                .forEach(player -> Bukkit.getOnlinePlayers()
                        .forEach(player1 -> {
                            if (!player1.hasPermission("command.vanish.bypass")){
                                player1.hidePlayer(plugin, player);
                            } else {
                                player1.showPlayer(plugin, player);
                            }
                        })
        ), 0L, 10L);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                final Player playerFinal = player;
                if (isVanished(player)) {
                    vanishedPlayers.remove(player);
                    Bukkit.getOnlinePlayers().forEach(player1 -> player1.showPlayer(Oracle.getInstance(), playerFinal));
                    player.sendMessage(ColorUtils.colored("&b&lOracle &8» &7You're no longer vanished."));
                } else {
                    vanishedPlayers.add(player);
                    player.sendMessage(ColorUtils.colored("&b&lOracle &8» &7You're now vanished."));
                }
            } else {
                player = Bukkit.getPlayerExact(args[0]);
                if (player == null) {
                    sender.sendMessage(ColorUtils.colored("&cPlayer not found."));
                    return true;
                }
                final Player playerFinal = player;
                if (isVanished(player)) {
                    vanishedPlayers.remove(player);
                    Bukkit.getOnlinePlayers().forEach(player1 -> player1.showPlayer(Oracle.getInstance(), playerFinal));
                    sender.sendMessage(ColorUtils.colored("&b&lOracle &8» &7" + player.getName() + " is no longer vanished."));
                } else {
                    vanishedPlayers.add(player);
                    sender.sendMessage(ColorUtils.colored("&b&lOracle &8» &7" + player.getName() + " is now vanished."));
                }
            }
        }
        return true;
    }
}
