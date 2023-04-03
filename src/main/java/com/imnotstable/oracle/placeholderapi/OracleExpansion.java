package com.imnotstable.oracle.placeholderapi;

import com.imnotstable.oracle.commands.ChatColorCommand;
import com.imnotstable.oracle.commands.NicknameCommand;
import com.imnotstable.oracle.commands.staff.VanishCommand;
import com.imnotstable.oracle.economy.BalanceTopCommand;
import com.imnotstable.oracle.stafftracker.DataManager;
import com.imnotstable.oracle.utils.PlayerDataUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class OracleExpansion extends PlaceholderExpansion {

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return "ImNotStable";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "Oracle";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    public @NotNull List<String> getPlaceholders() {
        return List.of("balancetop_#<number>", "balance_<player>", "isVanished", "balance", "nickname", "chatcolor", "stafftracker");
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.startsWith("balancetop_#")) {
            int place = Integer.parseInt(identifier.split("#")[1]);
            return PlayerDataUtils.getString(BalanceTopCommand.balanceTop.get(place), "username");
        }
        if (identifier.startsWith("balance_")) {
            UUID uuid = UUID.fromString(identifier.split("_")[1]);
            return String.valueOf(PlayerDataUtils.getDouble(uuid, "balance"));
        }
        if (identifier.equals("isVanished")) {
            return String.valueOf(VanishCommand.isVanished(player));
        }
        if (identifier.equals("balance")) {
            return String.valueOf(PlayerDataUtils.getDouble(player.getUniqueId(), "balance"));
        }
        if (identifier.equals("nickname")) {
            return NicknameCommand.getNicknameFormatted(player);
        }
        if (identifier.equals("chatcolor")) {
            return ChatColorCommand.getChatColor(player.getUniqueId());
        }
        if (identifier.equals("stafftracker")) {
            if (player.hasPermission("stafftracker.track")) {
                return String.valueOf(DataManager.getTime(player.getUniqueId()));
            }
            return "0";
        }
        return null;
    }
}