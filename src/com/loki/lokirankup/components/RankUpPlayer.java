package com.loki.lokirankup.components;

import com.loki.lokirankup.managers.ConfigManager;
import com.loki.lokirankup.components.ConfigComponents.*;
import com.loki.lokirankup.utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class RankUpPlayer {
    private Player player;
    private String name;
    private Rank rank;
    private int points, pointsToNextRank;
    private final Map<String, HomeObject> homes;
    private final LocalDateTime firstJoin;
    private final LocalDateTime lastLogin;

    public RankUpPlayer(Player player, String name, String rank, String points, Map<String, HomeObject> homes, String pointsToNextRank, String firstJoin, String lastLogin) {
        this.player = player;
        this.name = name;
        this.rank = ConfigManager.getRank(rank);
        this.points = Integer.parseInt(points);
        this.pointsToNextRank = Integer.parseInt(pointsToNextRank);
        this.firstJoin = LocalDateTime.parse(firstJoin);
        this.lastLogin = LocalDateTime.parse(lastLogin);
        this.homes = homes;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public Rank getRank() {
        return rank;
    }

    public double getPoints() {
        return points;
    }

    public int getPointsToNextRank() {
        return pointsToNextRank;
    }

    public LocalDateTime getFirstJoin() {
        return firstJoin;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Map<String, HomeObject> getAllHomes() {
        return homes;
    }

    public Map<String, HomeObject> getHomes() {
        Map<String, HomeObject> playerHomes = new HashMap<>();

        for (Map.Entry<String, HomeObject> home : homes.entrySet()) {
            if (!home.getValue().isPublic) {
                playerHomes.put(home.getKey(), home.getValue());
            }
        }

        return playerHomes;
    }

    public Map<String, HomeObject> getPublicHomes() {
        Map<String, HomeObject> playerHomes = new HashMap<>();

        for (Map.Entry<String, HomeObject> home : homes.entrySet()) {
            if (home.getValue().isPublic) {
                playerHomes.put(home.getKey(), home.getValue());
            }
        }

        return playerHomes;
    }

    public void createHome(String name, Location location) {
        HomeObject newHome = new HomeObject(location);

        this.homes.put(name, newHome);

        for (PlayerObject playerObject : ConfigManager.playersObjectMap) {
            if (playerObject.uuid.equals(player.getUniqueId().toString())) {
                playerObject.homes.put(name, newHome);
                ConfigManager.save(ConfigManager.playersObjectMap, ConfigManager.playersFile);
            }
        }
    }

    public void deleteHome(String name) {
        this.homes.remove(name);
        for (PlayerObject playerObject : ConfigManager.playersObjectMap) {
            if (playerObject.uuid.equals(player.getUniqueId().toString())) {
                playerObject.homes.remove(name);
                ConfigManager.save(ConfigManager.playersObjectMap, ConfigManager.playersFile);
            }
        }
    }

    public void setPublicHome(String name, Boolean isPublic) {
        this.homes.get(name).setPublic(isPublic);
        for (PlayerObject playerObject : ConfigManager.playersObjectMap) {
            if (playerObject.uuid.equals(player.getUniqueId().toString())) {
                playerObject.homes.get(name).setPublic(isPublic);
                ConfigManager.save(ConfigManager.playersObjectMap, ConfigManager.playersFile);
            }
        }
    }

    public double getPercentToNextLevel() {
        return (double) (points * 100) / pointsToNextRank;
    }

    public String getBar() {
        int percentX = Math.round((points * 15) / pointsToNextRank);

        StringBuilder progressCompleted = new StringBuilder("§a");
        StringBuilder progressNotCompleted = new StringBuilder("§7");

        for (int i = 0; i < percentX; i++) {
            progressCompleted.append("▍");
        }

        for (int i = 0; i < 15-percentX; i++) {
            progressNotCompleted.append("▍");
        }

        return progressCompleted+ progressNotCompleted.toString();
    }

    public void addPoints(int points) {
        this.points += points;

        if (this.points >= this.pointsToNextRank) {
            rankUp();
        } else {
            for (PlayerObject playerObject : ConfigManager.playersObjectMap) {
                if (playerObject.uuid.equals(player.getUniqueId().toString())) {
                    playerObject.points = this.points;
                    ConfigManager.save(ConfigManager.playersObjectMap, ConfigManager.playersFile);
                }
            }
        }
    }

    private void rankUp() {
        Rank nextRank = getRank().getNextRank();
        Rank maxRank = ConfigManager.getMaxRank();

        if(nextRank != null) {
            if (nextRank.equals(maxRank)) {
                for (Player target : Bukkit.getOnlinePlayers()) {
                    target.sendMessage("");
                    target.sendMessage("§a" + player.getName() + " chegou no rank máximo, " + nextRank.getName() + "§a!");
                    target.sendMessage("");
                }
                utils.spawnFireworks(player.getLocation(), 2);
            } else {
                player.sendMessage("");
                player.sendMessage("§aParabéns, você evoluiu para o rank " + nextRank.getName() + "§a.");
                player.sendMessage("");
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 19, 30);
            }

            this.points -= this.pointsToNextRank;
            this.rank = nextRank;
            this.pointsToNextRank = nextRank.getPointsToNextRank();

            for (PlayerObject playerObject : ConfigManager.playersObjectMap) {
                if (playerObject.uuid.equals(player.getUniqueId().toString())) {
                    playerObject.points = this.points;
                    playerObject.rank = nextRank.getId();
                    playerObject.pointsToNextRank = nextRank.getPointsToNextRank();
                    ConfigManager.save(ConfigManager.playersObjectMap, ConfigManager.playersFile);
                }
            }
        } else {
            for (PlayerObject playerObject : ConfigManager.playersObjectMap) {
                if (playerObject.uuid.equals(player.getUniqueId().toString())) {
                    playerObject.points = this.points;
                    ConfigManager.save(ConfigManager.playersObjectMap, ConfigManager.playersFile);
                }
            }
        }
    }
}
