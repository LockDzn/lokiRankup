package com.loki.lokirankup;

import com.loki.lokirankup.components.RankUpPlayer;
import com.loki.lokirankup.managers.ConfigManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.SortedMap;

public class groupAPI {

    private static String kickMessageGroupChanges = "§d§lRPG\n\n§cVocê foi kikado do servidor.\nSeu grupo foi alterado!\n\n";
    private static Scoreboard sb;

    public static void init() {
        sb = Bukkit.getScoreboardManager().getMainScoreboard();

        sb.registerNewTeam("00001master");
        sb.registerNewTeam("00002admin");
        sb.registerNewTeam("00003mod");
        sb.registerNewTeam("00004vip");
        sb.registerNewTeam("00005membro");

        sb.getTeam("00001master").setPrefix("§6[Master] ");
        sb.getTeam("00002admin").setPrefix("§c[Admin] ");
        sb.getTeam("00003mod").setPrefix("§2[Mod] ");
        sb.getTeam("00004vip").setPrefix("§b[VIP] ");
        sb.getTeam("00005membro").setPrefix("§7");
    }


    public static void setTag(Scoreboard scoreboard, Player player, String group) {

        String team = "";
        String prefix = "";

        if (group.equals("master")) {
            team = "00001master";
            prefix = "§6[Master] ";
        } else if (group.equals("admin"))  {
            team = "00002admin";
            prefix = "§c[Admin] ";
        } else if (group.equals("mod"))  {
            team = "00003mod";
            prefix = "§2[Mod] ";
        } else if (group.equals("vip"))  {
            team = "00004vip";
            prefix = "§b[VIP] ";
        } else if (group.equals("default"))  {
            prefix = "§7";
            team = "00005membro";
        }

        scoreboard.getTeam(team).addPlayer(player);
        player.setDisplayName(prefix + player.getName());
        player.setPlayerListName(prefix + player.getName());
        player.setCustomName(prefix + player.getName());

    }

    public static void createTeams(Scoreboard scoreboard) {
        scoreboard.registerNewTeam("00001master");
        scoreboard.registerNewTeam("00002admin");
        scoreboard.registerNewTeam("00003mod");
        scoreboard.registerNewTeam("00004vip");
        scoreboard.registerNewTeam("00005membro");

        scoreboard.getTeam("00001master").setPrefix("§6[Master] ");
        scoreboard.getTeam("00002admin").setPrefix("§c[Admin] ");
        scoreboard.getTeam("00003mod").setPrefix("§2[Mod] ");
        scoreboard.getTeam("00004vip").setPrefix("§b[VIP] ");
        scoreboard.getTeam("00005membro").setPrefix("§7");
    }

    public static void setOnJoin() {
        for (Player target : Bukkit.getOnlinePlayers()) {
            RankUpPlayer rankUpPlayer = ConfigManager.getPlayer(target.getUniqueId());
            scoreboardAPI.setOrUpdate(rankUpPlayer);

            Scoreboard scoreboard = target.getScoreboard();

            for (Player target2 : Bukkit.getOnlinePlayers()) {

                String group = utils.getPlayerGroup(target2, scoreboardAPI.groups);
                groupAPI.setTag(scoreboard, target2, group);
            }
        }
    }

}
