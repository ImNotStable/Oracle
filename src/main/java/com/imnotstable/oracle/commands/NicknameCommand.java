package com.imnotstable.oracle.commands;

import com.imnotstable.oracle.utils.ColorUtils;
import com.imnotstable.oracle.utils.PlayerDataUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class NicknameCommand implements CommandExecutor, Listener {

    private static final HashMap<Player, String> nicknames = new HashMap<>();

    public static String getNickname(Player player) {
        return nicknames.get(player);
    }

    public static String getNicknameFormatter(Player player) {
        String nickname = getNickname(player);
        if (!player.hasPermission("command.nickname.color")) {
            nickname = ChatColor.stripColor(nickname);
        }
        return nickname;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player && args.length != 0) {
            String nickname = args[0];
            if (Bukkit.getOfflinePlayerIfCached(nickname) != null || nicknames.containsValue(nickname)) {
                sender.sendMessage(ColorUtils.colored("&cThis name is already in use."));
                return true;
            }
            if (args.length == 2) {
                if (sender.hasPermission("command.nickname.other")) {
                    player = Bukkit.getPlayerExact(args[1]);
                    if (player == null) {
                        sender.sendMessage(ColorUtils.colored("&cPlayer not found."));
                        return true;
                    }
                }
            }
            nicknames.put(player, nickname);
            PlayerDataUtils.setString(player.getUniqueId(), "nickname", nickname);
        }
        return true;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String nickname = PlayerDataUtils.getString(player.getUniqueId(), "nickname");
        nicknames.put(player, nickname == null ? player.getName() : nickname);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        nicknames.remove(event.getPlayer());
    }
}
