package com.loki.lokirankup.listeners;

import com.loki.lokirankup.components.RankUpPlayer;
import com.loki.lokirankup.groupAPI;
import com.loki.lokirankup.managers.ConfigManager;
import com.loki.lokirankup.managers.NPCManager;
import com.loki.lokirankup.utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        RankUpPlayer rankUpPlayer = ConfigManager.getPlayer(player.getUniqueId());

        if(rankUpPlayer == null) {
            Bukkit.getConsoleSender().sendMessage("criando user");
            ConfigManager.createNewPlayer(player);
            rankUpPlayer = ConfigManager.getPlayer(player.getUniqueId());
        }

        rankUpPlayer.setPlayer(player);

        groupAPI.setOnJoin();
        NPCManager.loadAllNpcs(player);
        String joinMsg = "&eVocê tem " + rankUpPlayer.getPoints() + "/" + rankUpPlayer.getPointsToNextRank() +  " pontos de rank";
        player.sendMessage(utils.formatColor(joinMsg));

    }
}
