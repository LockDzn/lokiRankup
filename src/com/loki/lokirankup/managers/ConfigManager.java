package com.loki.lokirankup.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loki.lokirankup.components.ConfigComponents.*;
import com.loki.lokirankup.components.Rank;
import com.loki.lokirankup.components.RankGroup;
import com.loki.lokirankup.components.RankUpPlayer;
import com.loki.lokirankup.main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.*;

public class ConfigManager {
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static PlayerObject[] playersObjectMap;
    public static RanksFileObject ranksFileObject;

    public static Map<UUID, RankUpPlayer> playersMap = new HashMap<>();

    public static File playersFile = new File(main.plugin.getDataFolder(), "players.json");
    public static File ranksFile = new File(main.plugin.getDataFolder(), "ranks.json");

    public static void start() {
        loadRanks();
        loadPlayers();
    }

    public static void reload() {
        playersObjectMap = null;
        ranksFileObject = null;
        playersMap.clear();

        playersFile = new File(main.plugin.getDataFolder(), "players.json");
        ranksFile = new File(main.plugin.getDataFolder(), "ranks.json");
        start();
    }

    private static void loadPlayers() {
        try {
            if (!playersFile.exists()) {
                playersFile.createNewFile();
                List<RankUpPlayer> list = new ArrayList<>();
                save(list, playersFile);
            }

            playersObjectMap = gson.fromJson(new FileReader(playersFile), PlayerObject[].class);

            for (PlayerObject playerObject : playersObjectMap) {
                UUID uuid = UUID.fromString(playerObject.uuid);
                Player player = Bukkit.getPlayer(uuid);

                RankUpPlayer rankUpPlayer = new RankUpPlayer(player, playerObject.name, playerObject.rank,  Integer.toString(playerObject.points), playerObject.homes, Integer.toString(playerObject.pointsToNextRank), playerObject.firstJoin, playerObject.lastLogin);

                playersMap.put(uuid, rankUpPlayer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadRanks() {
        try {
            if (!ranksFile.exists()) {
                ranksFile.createNewFile();
                List<Rank> ranks = new ArrayList<>();
                ranks.add(new Rank("&cTerra III", "terra-iii", "&c[T-III]", "100", "terra-ii", "rank.terra", "terra", true));
                ranks.add(new Rank("&cTerra II", "terra-ii", "&c[T-II]", "1200", "terra-i", "rank.terra", "terra"));
                ranks.add(new Rank("&cTerra I", "terra-i", "&c[T-I]", "2350", "carvao-iii", "rank.terra", "terra"));
                ranks.add(new Rank("&8Carvão III", "carvao-iii", "&8[C-III]", "3500", null, "rank.carvao", "carvao"));

                List<RankGroup> groups = new ArrayList<>();
                groups.add(new RankGroup("&cTerra", "terra", "dirt", "11"));
                groups.add(new RankGroup("&8Carvão", "carvao", "coal_block", "13"));

                RanksFileObject list = new RanksFileObject(ranks, groups);
                save(list, ranksFile);
            }

            ranksFileObject = gson.fromJson(new FileReader(ranksFile), RanksFileObject.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(Object object, File file) {
        final String json = gson.toJson(object);
        try {
            file.delete();
            Files.write(file.toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<UUID, RankUpPlayer> getPlayers() {
        return playersMap;
    }

    public static RankUpPlayer getPlayer(UUID uuid) {
        return playersMap.get(uuid);
    }

    public static RankUpPlayer getPlayer(String name) {
        RankUpPlayer foundPlayer = null;

        for (Map.Entry<UUID, RankUpPlayer> player : playersMap.entrySet()) {
            if (player.getValue().getName().equals(name.toLowerCase())) {
                foundPlayer = player.getValue();
            }
        }

        return foundPlayer;
    }

    public static void createNewPlayer(Player player) {
        Rank defaultRank = getDefaultRank();
        Map<String, HomeObject> homes = new HashMap<>();

        RankUpPlayer rankUpPlayer = new RankUpPlayer(player, player.getName().toLowerCase(), defaultRank.getId(), "0", homes, Integer.toString(defaultRank.getPointsToNextRank()), LocalDateTime.now().toString(), LocalDateTime.now().toString());
        PlayerObject playerObject = new PlayerObject(player.getUniqueId().toString(), player.getName().toLowerCase(), defaultRank.getId(), 0, homes, defaultRank.getPointsToNextRank(), LocalDateTime.now().toString(), LocalDateTime.now().toString());

        if (playersObjectMap.length == 0) {
            List<PlayerObject> list = new ArrayList<>();
            list.add(playerObject);
            save(list, playersFile);
            loadPlayers();
        } else {
            List<PlayerObject> list = new ArrayList<>();
            for (PlayerObject playerObjectMap : playersObjectMap) {
                list.add(playerObjectMap);
            }
            list.add(playerObject);
            save(list, playersFile);
        }

        playersMap.put(player.getUniqueId(), rankUpPlayer);
    }

    public static Rank getDefaultRank() {
        Rank foundRank = null;
        for (Rank rank : ranksFileObject.ranks) {
            if (rank.isDefault() && foundRank == null) {
                foundRank = rank;
            }
        }
        return foundRank;
    }

    public static Rank getMaxRank() {
        Rank foundRank = null;
        for (Rank rank : ranksFileObject.ranks) {
            if (rank.getNextRank() == null && foundRank == null) {
                foundRank = rank;
            }
        }
        return foundRank;
    }

    public static List<Rank> getRanks() {
        return ranksFileObject.ranks;
    }

    public static Rank getRank(String id) {
        Rank foundRank = null;
        for (Rank rank : ranksFileObject.ranks) {
            if (rank.getId().equals(id)) {
                foundRank = rank;
            }
        }
        return foundRank;
    }

    public static List<RankGroup> getRanksGroups() {
        return ranksFileObject.groups;
    }

    public static RankGroup getRanksGroup(String id) {
        RankGroup foundGroup = null;
        for (RankGroup group : ranksFileObject.groups) {
            if (group.getId().equals(id)) {
                foundGroup = group;
            }
        }
        return foundGroup;
    }

    public static void test(UUID uuid) {

    }

}
