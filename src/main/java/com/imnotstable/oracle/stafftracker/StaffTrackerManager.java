package com.imnotstable.oracle.stafftracker;

import com.imnotstable.oracle.Oracle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Locale;

public class StaffTrackerManager implements Listener {

    public StaffTrackerManager(Oracle plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getOnlinePlayers().stream().filter(
                player -> player.hasPermission("stafftracker.track")).forEach(
                        player -> {
                            if ((System.currentTimeMillis() - afkTimer.getOrDefault(player, System.currentTimeMillis()) < 300_000L)) {
                                if (DataManager.getWeek(player) == getWeek()) {
                                    DataManager.addTime(player, 1L);
                                } else {
                                    DataManager.setWeek(player, getWeek());
                                    DataManager.setTime(player, 0L);
                                }
                            }
                        }
        ), 0L, 20L);
    }

    public static int getWeek() {
        LocalDate currentDate = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return currentDate.get(weekFields.weekOfWeekBasedYear());
    }
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("stafftracker.track")) {
            DataManager.createAccount(event.getPlayer());
            afkTimer.put(event.getPlayer(), System.currentTimeMillis());
        }
    }

    private static final HashMap<Player, Long> afkTimer = new HashMap<>();

    public static Long getAfkTimer(Player player) {
        return (System.currentTimeMillis() - afkTimer.getOrDefault(player, System.currentTimeMillis())) / 1000L;
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        if (event.getPlayer().hasPermission("stafftracker.track"))
            afkTimer.put(event.getPlayer(), System.currentTimeMillis());
    }

}
