package com.imnotstable.oracle;

import com.imnotstable.oracle.commands.*;
import com.imnotstable.oracle.commands.staff.ClearChatCommand;
import com.imnotstable.oracle.commands.staff.MuteChatCommand;
import com.imnotstable.oracle.commands.staff.StaffChatCommand;
import com.imnotstable.oracle.economy.BalanceCommand;
import com.imnotstable.oracle.economy.BalanceTopCommand;
import com.imnotstable.oracle.economy.EconomyCommand;
import com.imnotstable.oracle.economy.EconomyProvider;
import com.imnotstable.oracle.events.CancelledEvents;
import com.imnotstable.oracle.events.ChatEvent;
import com.imnotstable.oracle.events.JoinEvent;
import com.imnotstable.oracle.events.QuitEvent;
import com.imnotstable.oracle.placeholderapi.OracleExpansion;
import com.imnotstable.oracle.scoreboard.ScoreboardManager;
import com.imnotstable.oracle.stafftracker.DataManager;
import com.imnotstable.oracle.stafftracker.StaffTrackerCommand;
import com.imnotstable.oracle.stafftracker.StaffTrackerManager;
import com.imnotstable.oracle.utils.ColorUtils;
import com.imnotstable.oracle.utils.PlayerDataUtils;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Oracle extends JavaPlugin {

    @Override
    public void onEnable() {
        Log("");
        Log("&b █████&3╗ &b██████&3╗  &b█████&3╗  &b█████&3╗ &b██&3╗     &b███████&3╗");
        Log("&b██&3╔══&b██&3╗&b██&3╔══&b██&3╗&b██&3╔══&b██&3╗&b██&3╔══&b██&3╗&b██&3║     &b██&3╔════╝");
        Log("&b██&3║  &b██&3║&b██████&3╔╝&b███████&3║&b██&3║  ╚═╝&b██&3║     &b█████&3╗  ");
        Log("&b██&3║  &b██&3║&b██&3╔══&b██&3╗&b██&3╔══&b██&3║&b██&3║  &b██&3╗&b██&3║     &b██&3╔══╝  ");
        Log("&3╚&b█████&3╔╝&b██&3║  &b██&3║&b██&3║  &b██&3║╚&b█████&3╔╝&b███████&3╗&b███████&3╗");
        Log("&3 ╚════╝ ╚═╝  ╚═╝╚═╝  ╚═╝ ╚════╝ ╚══════╝╚══════╝");
        Log("");
        instance = this;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, PlayerDataUtils::saveAllConfigurations, 0L, 1200L);
        //Chat
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Chat> chatRSP = getServer().getServicesManager().getRegistration(Chat.class);
            if (chatRSP != null) {
                chat = chatRSP.getProvider();
                Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
                Objects.requireNonNull(getCommand("chatcolor")).setExecutor(new ChatColorCommand());
                Bukkit.getPluginManager().registerEvents(new ChatColorCommand(), this);
            }
            //Economy
            economyProvider = new EconomyProvider();
            Bukkit.getServicesManager().register(Economy.class, economyProvider, this, ServicePriority.Normal);
            RegisteredServiceProvider<Economy> econRSP = Bukkit.getServicesManager().getRegistration(Economy.class);
            if (econRSP != null) {
                econ = econRSP.getProvider();
                Objects.requireNonNull(getCommand("economy")).setExecutor(new EconomyCommand());
                Objects.requireNonNull(getCommand("balance")).setExecutor(new BalanceCommand());
                Objects.requireNonNull(getCommand("balancetop")).setExecutor(new BalanceTopCommand());
                Bukkit.getScheduler().scheduleSyncRepeatingTask(this, BalanceTopCommand::updateBalanceTop, 0L, 1200L);
                Log("&bImplemented Economy.");
            }
        }
        //PlaceholderAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new OracleExpansion().register();
            Log("&bImplemented PlaceholderAPI.");
        }
        //Scoreboard
        new ScoreboardManager(this);
        //Staff Tracker
        DataManager.loadAllConfigurations();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().stream().filter(
                player -> player.hasPermission("stafftracker.track")).forEach(
                player -> {
                    DataManager.loadedConfigurations.get(player.getUniqueId()).set("username", player.getName());
                    DataManager.save(DataManager.getFile(player.getUniqueId()), DataManager.loadedConfigurations.get(player.getUniqueId()));
                }
        ), 0L, 200L);
        DataManager.saveAllConfigurations();
        new StaffTrackerManager(this);
        //Commands
        Objects.requireNonNull(getCommand("staffchat")).setExecutor(new StaffChatCommand());
        Objects.requireNonNull(getCommand("mutechat")).setExecutor(new MuteChatCommand());
        Objects.requireNonNull(getCommand("clearchat")).setExecutor(new ClearChatCommand());
        Objects.requireNonNull(getCommand("stafftracker")).setExecutor(new StaffTrackerCommand());
        Bukkit.getPluginManager().registerEvents(new StaffTrackerCommand(), this);
        Objects.requireNonNull(getCommand("nickname")).setExecutor(new NicknameCommand());
        Bukkit.getPluginManager().registerEvents(new NicknameCommand(), this);
        Objects.requireNonNull(getCommand("message")).setExecutor(new MessageCommand());
        Objects.requireNonNull(getCommand("reply")).setExecutor(new ReplyCommand());
        //Events
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new QuitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new CancelledEvents(), this);
    }

    @Override
    public void onDisable() {
        PlayerDataUtils.saveAllConfigurations();
    }

    public static void Log(String message) {
        Bukkit.getConsoleSender().sendMessage(ColorUtils.colored(message));
    }


    private static Oracle instance;

    public static Oracle getInstance() {
        return instance;
    }

    private static Chat chat = null;

    public static Chat getChat() {
        return chat;
    }

    public EconomyProvider economyProvider;

    private static Economy econ = null;

    public static Economy getEconomy() {
        return econ;
    }
}
