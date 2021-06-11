package com.loki.lokirankup.commands.home;

import com.loki.lokirankup.components.RankUpPlayer;
import com.loki.lokirankup.managers.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEsse comando só pode ser executado por um jogador.");
            return true;
        }

        Player player = (Player) sender;
        RankUpPlayer rankUpPlayer = ConfigManager.getPlayer(player.getUniqueId());

        if (args.length == 0) {
            player.sendMessage("§cUse /delhome <name>.");
            return true;
        }

        String homeName = args[0].toLowerCase();

        if (rankUpPlayer.getHomes().containsKey(homeName)) {
            rankUpPlayer.deleteHome(homeName);
            player.sendMessage("§aHome deletada com sucesso.");
        } else {
            player.sendMessage("§cHome não encontrada.");
        }

        return false;
    }
}
