package com.loki.lokirankup.listeners;

import com.loki.lokirankup.components.Rank;
import com.loki.lokirankup.components.RankUpPlayer;
import com.loki.lokirankup.managers.ConfigManager;
import com.loki.lokirankup.utils;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class onChat implements Listener {

    public static Boolean localMuted = false;
    public static Boolean globalMuted = false;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (localMuted && !player.hasPermission("chat.staff")) {
            player.sendMessage("§cO chat local está desativado.");
            event.setCancelled(true);
            return;
        }

        RankUpPlayer rankUpPlayer = ConfigManager.getPlayer(player.getUniqueId());
        Rank playerRank = rankUpPlayer.getRank();
        String message = event.getMessage();

        if (player.hasPermission("chat.colors")) {
            message = utils.formatColor(message);
        } else {
            message = "§7" + message;
        }

        int uppercases = utils.checkUppercaseMessage(message);
        if (uppercases > 5 && !player.hasPermission("chat.bypasscheck")) {
            player.sendMessage("§cPorfavor, manere na utilização de CapsLock!");
            event.setCancelled(true);
            return;
        }

        List<Entity> nearbyEntities = player.getNearbyEntities(15, 15, 15);

        TextComponent localSymbol = utils.createHolverChat("§e[l] ", "§eChat local\n\n§fApenas jogadores próximos podem ver essa mensagem.");
        TextComponent rankPrefix = utils.createHolverChat(playerRank.getPrefix() + " ", "§fRank: "+ playerRank.getName() + "\n§fPontos: §7" + rankUpPlayer.getPoints());
        TextComponent playerName = utils.createHolverChat(player.getDisplayName() + ":", "§fNick: §7" + player.getName() + "\n§fMoney: §70\nPontos: §7" + rankUpPlayer.getPoints());
        TextComponent messegeFormated = utils.createHolverChat(" " + message, null);

        for (Entity entity : player.getNearbyEntities(15, 15, 15)) {
            if (!(entity instanceof Player)) return;

            ((Player) entity).getPlayer().spigot().sendMessage(localSymbol, rankPrefix, playerName, messegeFormated);

        }
        player.spigot().sendMessage(localSymbol, rankPrefix, playerName, messegeFormated);
        event.setFormat(localSymbol.getText() + rankPrefix.getText() + playerName.getText() + messegeFormated.getText());


        if (nearbyEntities.size() == 0) {
            player.sendMessage("§eNinguém por perto para ouvir você.");
        }

        event.setCancelled(true);
        return;

    }
}
