package com.imnotstable.oracle.economy;

import com.imnotstable.oracle.Oracle;
import com.imnotstable.oracle.utils.PlayerDataUtils;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

import java.util.Collections;
import java.util.List;

public class EconomyProvider implements Economy {
    @Override
    public boolean isEnabled() {
        return Oracle.getEconomy() != null;
    }

    @Override
    public String getName() {
        return "Oracle Economy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return -1;
    }

    @Override
    public String format(double amount) {
        return String.valueOf(amount);
    }

    @Override
    public String currencyNamePlural() {
        return "Dollars";
    }

    @Override
    public String currencyNameSingular() {
        return "Dollar";
    }

    @Override
    public boolean hasAccount(String player) {
        return PlayerDataUtils.configurationExists(PlayerDataUtils.getUUID(player));
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return PlayerDataUtils.configurationExists(PlayerDataUtils.getUUID(player));
    }

    @Override
    public boolean hasAccount(String player, String worldName) {
        return PlayerDataUtils.configurationExists(PlayerDataUtils.getUUID(player));
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return PlayerDataUtils.configurationExists(PlayerDataUtils.getUUID(player));
    }

    @Override
    public double getBalance(String player) {
        return PlayerDataUtils.getDouble(PlayerDataUtils.getUUID(player), "balance");
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        return PlayerDataUtils.getDouble(PlayerDataUtils.getUUID(player), "balance");
    }

    @Override
    public double getBalance(String player, String world) {
        return PlayerDataUtils.getDouble(PlayerDataUtils.getUUID(player), "balance");
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return PlayerDataUtils.getDouble(PlayerDataUtils.getUUID(player), "balance");
    }

    @Override
    public boolean has(String player, double amount) {
        return PlayerDataUtils.getDouble(PlayerDataUtils.getUUID(player), "balance") >= amount;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return PlayerDataUtils.getDouble(PlayerDataUtils.getUUID(player), "balance") >= amount;
    }

    @Override
    public boolean has(String player, String worldName, double amount) {
        return PlayerDataUtils.getDouble(PlayerDataUtils.getUUID(player), "balance") >= amount;
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return PlayerDataUtils.getDouble(PlayerDataUtils.getUUID(player), "balance") >= amount;
    }

    @Override
    public EconomyResponse withdrawPlayer(String player, double amount) {
        if (player == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player cannot be null.");
        }
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw a negative amount.");
        }
        if (!PlayerDataUtils.configurationExists(PlayerDataUtils.getUUID(player))) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player doesn't exist.");
        }
        if (PlayerDataUtils.getDouble(PlayerDataUtils.getUUID(player), "balance") >= amount) {
            PlayerDataUtils.setDouble(PlayerDataUtils.getUUID(player), "balance",PlayerDataUtils.getDouble(PlayerDataUtils.getUUID(player), "balance") - amount);
            return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null);
        }
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        if (player == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player cannot be null.");
        }
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw a negative amount.");
        }
        if (!PlayerDataUtils.configurationExists(PlayerDataUtils.getUUID(player))) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player doesn't exist.");
        }
        if (PlayerDataUtils.getDouble(PlayerDataUtils.getUUID(player), "balance") >= amount) {
            PlayerDataUtils.setDouble(PlayerDataUtils.getUUID(player), "balance",PlayerDataUtils.getDouble(PlayerDataUtils.getUUID(player), "balance") - amount);
            return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null);
        }
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(String player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String player, double amount) {
        if (player == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player cannot be null.");
        }
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot deposit a negative amount.");
        }
        if (!PlayerDataUtils.configurationExists(PlayerDataUtils.getUUID(player))) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player doesn't exist.");
        }
        PlayerDataUtils.setDouble(PlayerDataUtils.getUUID(player), "balance",PlayerDataUtils.getDouble(PlayerDataUtils.getUUID(player), "balance") + amount);
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        if (player == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player cannot be null.");
        }
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot deposit a negative amount.");
        }
        if (!PlayerDataUtils.configurationExists(PlayerDataUtils.getUUID(player))) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player doesn't exist.");
        }
        PlayerDataUtils.setDouble(PlayerDataUtils.getUUID(player), "balance",PlayerDataUtils.getDouble(PlayerDataUtils.getUUID(player), "balance") + amount);
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(String player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    @Override
    public boolean createPlayerAccount(String player) {
        return PlayerDataUtils.loadConfiguration(PlayerDataUtils.getUUID(player));
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        return PlayerDataUtils.loadConfiguration(PlayerDataUtils.getUUID(player));
    }

    @Override
    public boolean createPlayerAccount(String player, String worldName) {
        return createPlayerAccount(player);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return createPlayerAccount(player);
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Oracle does not support bank accounts.");
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Oracle does not support bank accounts.");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Oracle does not support bank accounts.");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Oracle does not support bank accounts.");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Oracle does not support bank accounts.");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Oracle does not support bank accounts.");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Oracle does not support bank accounts.");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Oracle does not support bank accounts.");
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Oracle does not support bank accounts.");
    }

    @Override
    public EconomyResponse isBankMember(String name, String player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Oracle does not support bank accounts.");
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Oracle does not support bank accounts.");
    }

    @Override
    public List<String> getBanks() {
        return Collections.emptyList();
    }
}
