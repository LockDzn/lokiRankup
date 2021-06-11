package com.loki.lokirankup.commands.home;

import com.loki.lokirankup.components.RankUpPlayer;
import com.loki.lokirankup.managers.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEsse comando só pode ser executado por um jogador.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUse /sethome <name>.");
            return true;
        }

        Player player = (Player) sender;
        RankUpPlayer rankUpPlayer = ConfigManager.getPlayer(player.getUniqueId());

        String homeName = args[0].toLowerCase();

        rankUpPlayer.createHome(homeName, player.getLocation());
        player.sendMessage("§aHome \"" + homeName + "\" criada com sucesso!");
        return true;
    }
}
