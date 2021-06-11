package com.loki.lokirankup.commands.chat;

import com.loki.lokirankup.components.Rank;
import com.loki.lokirankup.components.RankUpPlayer;
import com.loki.lokirankup.listeners.onChat;
import com.loki.lokirankup.managers.ConfigManager;
import com.loki.lokirankup.utils;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GlobalChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        RankUpPlayer rankUpPlayer = ConfigManager.getPlayer(player.getUniqueId());
        Rank playerRank = rankUpPlayer.getRank();
        String message = "";

        if (args.length == 0) {
            player.sendMessage("§cUse /g <mensagem>");
            return true;
        }

        if (onChat.globalMuted && !player.hasPermission("chat.staff")) {
            player.sendMessage("§cO chat global está desativado.");
            return true;
        }

        for (String arg : args) {
            message += arg;
        }

        int uppercases = utils.checkUppercaseMessage(message);
        if (uppercases > 5 && !player.hasPermission("chat.bypasscheck")) {
            player.sendMessage("§cPorfavor, manere na utilização de CapsLock!");
            return true;
        }

        if (player.hasPermission("chat.colors")) {
            message = utils.formatColor(message);
        } else {
            message = "§7" + message;
        }

        TextComponent localSymbol = utils.createHolverChat("§7[g] ", "§7Chat global\n\n§fTodos jogadores podem ver essa mensagem.");
        TextComponent rankPrefix = utils.createHolverChat(playerRank.getPrefix() + " ", "§fRank: "+ playerRank.getName() + "\n§fPontos: §7" + rankUpPlayer.getPoints());
        TextComponent playerName = utils.createHolverChat(player.getDisplayName() + ":", "§fNick: §7" + player.getName() + "\n§fMoney: §70\nPontos: §7" + rankUpPlayer.getPoints());
        TextComponent messegeFormated = utils.createHolverChat(" " + message, null);

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("chat.spaces")) {
                target.sendMessage("");
                target.spigot().sendMessage(localSymbol, rankPrefix, playerName, messegeFormated);
                target.sendMessage("");
            } else {
                target.spigot().sendMessage(localSymbol, rankPrefix, playerName, messegeFormated);
            }
        }

        return false;
    }
}
