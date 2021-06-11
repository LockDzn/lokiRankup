package com.loki.lokirankup.commands.rankup;

import com.loki.lokirankup.managers.ConfigManager;
import com.loki.lokirankup.utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (!player.isOp()) {
            player.sendMessage(utils.formatColor("&cVocê não tem permissão para executar esse comando."));
        }

        ConfigManager.reload();
        player.sendMessage(utils.formatColor("&aReload feito com sucesso!"));
        return true;
    }
}
