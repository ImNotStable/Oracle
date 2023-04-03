package com.imnotstable.oracle.events;

import com.imnotstable.oracle.utils.ColorUtils;
import com.imnotstable.oracle.utils.PlayerDataUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {

    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.joinMessage(ColorUtils.coloredComponent("&8[&a+&8] &7" + player.getName()));
        PlayerDataUtils.setString(player.getUniqueId(), "username", player.getName());
    }

}
