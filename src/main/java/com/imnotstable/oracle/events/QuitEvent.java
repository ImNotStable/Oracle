package com.imnotstable.oracle.events;

import com.imnotstable.oracle.utils.ColorUtils;
import com.imnotstable.oracle.utils.PlayerDataUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {
    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.quitMessage(ColorUtils.coloredComponent("&8[&c-&8] &7" + player.getName()));
        PlayerDataUtils.saveConfiguration(player.getUniqueId());
    }
}
