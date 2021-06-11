package com.loki.lokirankup.commands.chat;

import com.loki.lokirankup.listeners.onChat;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (!player.hasPermission("chat.manege")) {
            player.sendMessage("§cVocê não tem permissão para executar esse comando.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§eComandos para o chat:");
            player.sendMessage("");
            player.sendMessage("   §e/chat mute <local/global>");
            player.sendMessage("   §e/chat unmute <local/global>");
            player.sendMessage("   §e/chat clear");
            return true;
        }

        switch (args[0]) {
            case "mute":
                if (args.length < 2) {
                    player.sendMessage("§cUse /chat mute <local/global>");
                    break;
                }

                if (args[1].equals("local")) {
                    if (onChat.localMuted) {
                        player.sendMessage("§cChat local já está mutado.");
                    } else {
                        onChat.localMuted = true;
                        player.sendMessage("§aChat local foi mutado.");
                    }
                    break;
                } else if (args[1].equals("global")) {
                    if (onChat.globalMuted) {
                        player.sendMessage("§cChat global já está mutado.");
                    } else {
                        onChat.globalMuted = true;
                        player.sendMessage("§aChat global foi mutado.");
                    }
                    break;
                }
            case "unmute":
                if (args.length < 2) {
                    player.sendMessage("§cUse /chat unmute <local/global>");
                    break;
                }

                if (args[1].equals("local")) {
                    if (!onChat.localMuted) {
                        player.sendMessage("§cChat local não está mutado.");
                    } else {
                        onChat.localMuted = false;
                        player.sendMessage("§aChat local foi desmutado.");
                    }
                    break;
                } else if (args[1].equals("global")) {
                    if (!onChat.globalMuted) {
                        player.sendMessage("§cChat global não está mutado.");
                    } else {
                        onChat.globalMuted = false;
                        player.sendMessage("§aChat global foi desmutado.");
                    }
                    break;
                }
            case "clear":
                Bukkit.broadcastMessage(StringUtils.repeat(" \n", 100));
                Bukkit.broadcastMessage("§eChat foi limpo.");
                break;
            default:
                player.sendMessage("§eComandos para o chat:");
                player.sendMessage("");
                player.sendMessage("   §e/chat mute <local/global>");
                player.sendMessage("   §e/chat unmute <local/global>");
                player.sendMessage("   §e/chat clear");
                break;
        }

        return false;
    }
}
