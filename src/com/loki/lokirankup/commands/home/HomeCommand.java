package com.loki.lokirankup.commands.home;

import com.loki.lokirankup.components.ConfigComponents;
import com.loki.lokirankup.components.RankUpPlayer;
import com.loki.lokirankup.main;
import com.loki.lokirankup.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Locale;
import java.util.Map;

public class HomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEsse comando só pode ser executado por um jogador.");
            return true;
        }

        Player player = (Player) sender;
        RankUpPlayer rankUpPlayer = ConfigManager.getPlayer(player.getUniqueId());

        String publicHomesStr = "§7Nenhuma.";
        String privateHomesStr = "§7Nenhuma.";

        if (rankUpPlayer.getHomes().size() > 0) {
            privateHomesStr = "";
            for (Map.Entry<String, ConfigComponents.HomeObject> home : rankUpPlayer.getHomes().entrySet()) {
                privateHomesStr += "§f" + home.getKey() + "§7, ";
            }
        }
        if (rankUpPlayer.getPublicHomes().size() > 0) {
            publicHomesStr = "";
            for (Map.Entry<String, ConfigComponents.HomeObject> home : rankUpPlayer.getPublicHomes().entrySet()) {
                publicHomesStr += "§f" + home.getKey() + "§7, ";
            }
        }



        if (args.length == 0) {
            sender.sendMessage("§cUse /home <name> [-publica].");
            sender.sendMessage("");
            sender.sendMessage("§dHomes públicas: " + publicHomesStr);
            sender.sendMessage("§dHomes privadas: " + privateHomesStr);
            return true;
        }

        String homeName = args[0].toLowerCase();

        if (args.length == 2){
            RankUpPlayer rankUpPlayer1 = ConfigManager.getPlayer(args[0].toLowerCase());

            if (rankUpPlayer1 == null) {
                sender.sendMessage("§cJogador não encontrado.");
                return true;
            }

            homeName = args[1].toLowerCase();

            if (rankUpPlayer1.getPublicHomes().containsKey(homeName)) {
                ConfigComponents.HomeObject playerHome = rankUpPlayer1.getPublicHomes().get(homeName);
                teleportPlayer(player, playerHome, homeName);
            } else {
                player.sendMessage("§cHome não encontrada.");
            }
            return true;
        }

        if (rankUpPlayer.getAllHomes().containsKey(homeName)) {
            ConfigComponents.HomeObject home = rankUpPlayer.getAllHomes().get(homeName);
            teleportPlayer(player, home, homeName);
        } else {
            player.sendMessage("§cHome não encontrada.");
        }
        return false;
    }

    private void teleportPlayer(Player player, ConfigComponents.HomeObject home, String homeName) {
        if (player.hasPermission("rankup.instantteleport")) {
            player.teleport(home.getLocation());
            player.sendMessage("§aTeleportado com sucesso.");
            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 19, 30);
        } else {
            player.sendMessage("§aEm 3 segundos você irá para a home \"" + homeName + "\".");
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(home.getLocation());
                    player.sendMessage("§aTeleportado com sucesso.");
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 19, 30);
                }
            }.runTaskLater(main.plugin, 20L * 3);
        }
    }
}
