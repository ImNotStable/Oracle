package com.imnotstable.oracle.scoreboard;


import com.imnotstable.oracle.Oracle;
import com.imnotstable.oracle.utils.ColorUtils;
import com.imnotstable.oracle.utils.NumberUtils;
import com.imnotstable.oracle.utils.PlayerDataUtils;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class ScoreboardManager implements Listener {
    public static final HashMap<UUID, FastBoard> Scoreboards = new HashMap<>();

    public ScoreboardManager(Oracle plugin) {
        Bukkit.getPluginManager().registerEvents(this, Oracle.getInstance());
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (FastBoard board : Scoreboards.values()) {
                Player player = board.getPlayer();
                updateBoard(board,
                        "",
                        " &b&l" + player.getName(),
                        "  &3Balance: &f$" + NumberUtils.formatSuffix(PlayerDataUtils.getDouble(player.getUniqueId(), "balance")),
                        "  &3Tokens: &f" + NumberUtils.formatSuffix(PlayerDataUtils.getDouble(player.getUniqueId(), "tokens")),
                        "",
                        " &b&lServer",
                        "  &3Online: &f" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(),
                        "  &3Joins: &f" + Bukkit.getOfflinePlayers().length,
                        "  &3TPS: &f" + Math.round(Bukkit.getServer().getTPS()[0] * 100) / 100,
                        "",
                        "&7Oracle.gg"
                );
            }
        }, 0L, 10L);
    }

    private void updateBoard(FastBoard board, String... lines) {
        for (int i = 0; i < lines.length; ++i) {
            lines[i] = ColorUtils.colored(lines[i]);
        }
        board.updateLines(lines);
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FastBoard board = new FastBoard(player);
        board.updateTitle(ColorUtils.colored("&b&lOracle &8| &3v1.0.0"));
        ScoreboardManager.Scoreboards.put(player.getUniqueId(), board);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        FastBoard board = ScoreboardManager.Scoreboards.remove(player.getUniqueId());
        if (board != null)
            board.delete();
    }
}