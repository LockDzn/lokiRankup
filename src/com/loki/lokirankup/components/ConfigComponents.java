package com.loki.lokirankup.components;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;
import java.util.Map;

public class ConfigComponents {

    public static class ConfigObject {
        public String serverName;
        public String motd;
        public Map<String, WarpObject> warps;

        public ConfigObject(String serverName, String motd) {
            this.serverName = serverName;
            this.motd = motd;
        }
    }

    public static class PlayerObject {
        public String uuid;
        public String name;
        public String rank;
        public int points;
        public int pointsToNextRank;
        public Map<String, HomeObject> homes;
        public String lastLogin;
        public String firstJoin;

        public PlayerObject(String uuid, String name, String rank, int points, Map<String, HomeObject> homes, int pointsToNextRank, String lastLogin, String firstJoin) {
            this.uuid = uuid;
            this.name = name;
            this.rank = rank;
            this.points = points;
            this.homes = homes;
            this.pointsToNextRank = pointsToNextRank;
            this.lastLogin = lastLogin;
            this.firstJoin = firstJoin;
        }
    }

    public static class WarpObject {
        public String world;
        public double x;
        public double y;
        public double z;
        public float yaw;
        public float pitch;

        public WarpObject(Location location) {
            this.world = location.getWorld().getName();
            this.x = location.getX();
            this.y = location.getY();
            this.z = location.getZ();
            this.yaw = location.getYaw();
            this.pitch = location.getPitch();
        }

        public WarpObject(String world, double x, double y, double z, float yaw, float pitch) {
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public Location getLocation() {
            return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        }
    }

    public static class HomeObject {
        public String world;
        public double x;
        public double y;
        public double z;
        public float yaw;
        public float pitch;
        public Boolean isPublic = false;

        public HomeObject(Location location) {
            this.world = location.getWorld().getName();
            this.x = location.getX();
            this.y = location.getY();
            this.z = location.getZ();
            this.yaw = location.getYaw();
            this.pitch = location.getPitch();
        }

        public HomeObject(String world, double x, double y, double z, float yaw, float pitch) {
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public void setPublic(Boolean aPublic) {
            isPublic = aPublic;
        }

        public Location getLocation() {
            return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        }
    }

    public static class RanksFileObject {
        public List<Rank> ranks;
        public List<RankGroup> groups;

        public RanksFileObject(List<Rank> ranks, List<RankGroup> groups) {
            this.ranks = ranks;
            this.groups = groups;
        }
    }

}
