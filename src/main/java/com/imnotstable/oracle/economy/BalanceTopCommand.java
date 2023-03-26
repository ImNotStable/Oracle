package com.imnotstable.oracle.economy;

import com.imnotstable.oracle.utils.ColorUtils;
import com.imnotstable.oracle.utils.NumberUtils;
import com.imnotstable.oracle.utils.PlayerDataUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public class BalanceTopCommand implements CommandExecutor {
    public static final HashMap<Integer, UUID> balanceTop = new HashMap<>();

    public static double serverTotal = 0;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        int page = 1;
        if (args.length > 0) {
            if (!args[0].equals("update")) {
                try {
                    page = Integer.parseInt(args[0]);
                } catch (NumberFormatException ignored) {
                }
            } else if (sender.hasPermission("command.balancetop.update")) {
                updateBalanceTop();
                return true;
            }
        }
        int b = page * 10;
        int index = b - 9;
        sender.sendMessage(ColorUtils.colored("&7&l&m•                &b&l Oracle &7&l&m              " + " &b&l" + page + "&7/&b&l" + (int) Math.ceil(balanceTop.size() * 0.1) + " &7&m                •"));
        sender.sendMessage(ColorUtils.colored("&8[&b&l!&8] &b&lOracle &7→ Server Total: $" + NumberUtils.formatCommas(serverTotal)));
        while (index <= b) {
            if (balanceTop.containsKey(index)) {
                sender.sendMessage(ColorUtils.colored("&7" + index + ". " + PlayerDataUtils.getString(balanceTop.get(index), "username") + ", &b$" + NumberUtils.formatCommas(PlayerDataUtils.getDouble(balanceTop.get(index), "balance"))));
                index++;
                continue;
            }
            break;
        }
        sender.sendMessage(ColorUtils.colored("&8[&b&l!&8] &b&lOracle &7→ Type /baltop 2 to read the next page."));
        return true;
    }

    public static void updateBalanceTop() {
        File folder = new File("plugins/Oracle/playerdata/");
        if (folder.listFiles() != null) {
            serverTotal = 0;
            HashMap<UUID, Double> playerBalanceMap = new HashMap<>();
            Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                    .filter(file -> file.getName().endsWith(".yml"))
                    .forEach(file -> {
                        UUID uuid = UUID.fromString(file.getName().replaceAll(".yml", ""));
                        double balance = PlayerDataUtils.getDouble(uuid, "balance");
                        playerBalanceMap.put(uuid, balance);
                        serverTotal += balance;
                    });
            balanceTop.clear();

            List<Map.Entry<UUID, Double>> list = new LinkedList<>(playerBalanceMap.entrySet());

            list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            int index = 1;
            for (Map.Entry<UUID, Double> entry : list) {
                balanceTop.put(index, entry.getKey());
                index++;
            }
        }
    }
}
