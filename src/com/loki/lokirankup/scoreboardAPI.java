package com.loki.lokirankup;

import com.loki.lokirankup.components.Rank;
import com.loki.lokirankup.components.RankUpPlayer;
import com.loki.lokirankup.managers.ConfigManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class scoreboardAPI {

    HashMap<Player, Scoreboard> scores = new HashMap<>();
    public static ArrayList<String> groups = new ArrayList<>();

    public static void start() {
        groups.add("master");
        groups.add("admin");
        groups.add("mod");
        groups.add("vip");
        groups.add("membro");

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player target : Bukkit.getOnlinePlayers()) {
                    RankUpPlayer rankUpPlayer = ConfigManager.getPlayer(target.getUniqueId());
                    setOrUpdate(rankUpPlayer);

                    Scoreboard scoreboard = target.getScoreboard();

                    for (Player target2 : Bukkit.getOnlinePlayers()) {

                        String group = utils.getPlayerGroup(target2, groups);
                        groupAPI.setTag(scoreboard, target2, group);
                    }
                }
            }
        }.runTaskTimer(main.plugin, 0L, 100*1);
    }

    public static void setOrUpdate(RankUpPlayer rankUpPlayer) {
        Player player = rankUpPlayer.getPlayer();
        Rank rank = rankUpPlayer.getRank();

        String points = utils.formatColor("&7"+ rankUpPlayer.getPoints() + "/"+ rank.getPointsToNextRank());
        String percent = utils.formatColor("&7"+ String.format("%.2f", rankUpPlayer.getPercentToNextLevel()) + "%");
        String bar = utils.formatColor(""+ rankUpPlayer.getBar());

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        groupAPI.createTeams(scoreboard);

        Objective objective = scoreboard.registerNewObjective("score", "");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§d§lTEST RANKUP");

        objective.getScore(" ").setScore(9);
        objective.getScore("§fRank: §r" + rank.getName()).setScore(8);
        objective.getScore("§fPontos: §r" + points).setScore(7);
        objective.getScore("§7" + bar + " §r" + percent).setScore(6);
        objective.getScore("§f ").setScore(5);
        objective.getScore("§fClan: §7Nenhum").setScore(4);
        objective.getScore("§a ").setScore(3);
        objective.getScore("§fCoins: §d0").setScore(2);
        objective.getScore("§b ").setScore(1);
        objective.getScore("§dwww.site.com").setScore(0);

        player.setScoreboard(scoreboard);
    }


}
