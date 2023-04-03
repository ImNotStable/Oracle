package com.imnotstable.oracle.events;

import com.imnotstable.oracle.Oracle;
import com.imnotstable.oracle.commands.ChatColorCommand;
import com.imnotstable.oracle.commands.NicknameCommand;
import com.imnotstable.oracle.commands.staff.MuteChatCommand;
import com.imnotstable.oracle.commands.staff.StaffChatCommand;
import com.imnotstable.oracle.utils.ColorUtils;
import com.imnotstable.oracle.utils.PlayerDataUtils;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static com.imnotstable.oracle.Oracle.getChat;

public class ChatEvent implements Listener {

    @EventHandler
    public void onAsyncChatEvent(AsyncChatEvent event) {
        if (StaffChatCommand.staffChat.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            String displayName = getChat().getPlayerGroups(event.getPlayer())[0] + " " + event.getPlayer().getName();
            StaffChatCommand.sendStaffChat(displayName, PlainTextComponentSerializer.plainText().serialize(event.message()));
            return;
        }
        if (MuteChatCommand.isChatMuted() && event.getPlayer().hasPermission("command.mutechat.bypass")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ColorUtils.colored("&cChat was muted."));
            return;
        }
        event.renderer(ChatRenderer.viewerUnaware(this::formatChatMessage));
    }

    public Component formatChatMessage(Player player, Component displayName, Component message) {
        Component username = Component.text(NicknameCommand.getNicknameFormatted(player)).hoverEvent(ColorUtils.coloredComponent(
                "&3" +
                        player.getName() +
                        "'s Statistics\n\n" +
                        "&3Statistics\n" +
                        "&7Rank &8» " +
                        Oracle.getChat().getPlayerPrefix(player) +
                        "\n" +
                        "&7Balance &8» &a$" +
                        PlayerDataUtils.getDouble(player.getUniqueId(), "balance") +
                        "\n" +
                        "&7Tokens &8» &b⛃" +
                        PlayerDataUtils.getDouble(player.getUniqueId(), "tokens") +
                        "\n"
        ));
        message = message.hoverEvent(ColorUtils.coloredComponent("&cClick to report.")).clickEvent(ClickEvent.runCommand("/report " + player.getName() + " " + message + " " + System.currentTimeMillis()));
        return Component.join(JoinConfiguration.noSeparators(),
                        ColorUtils.coloredComponent(Oracle.getChat().getPlayerPrefix(player)),
                        username,
                        ColorUtils.coloredComponent(Oracle.getChat().getPlayerSuffix(player) +
                        " &8» " +
                        ChatColorCommand.getChatColor(player.getUniqueId())),
                        message
        );
    }
}
