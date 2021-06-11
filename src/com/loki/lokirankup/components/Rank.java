package com.loki.lokirankup.components;

import com.loki.lokirankup.managers.ConfigManager;
import com.loki.lokirankup.utils;

public class Rank {
    private String name;
    private String id;
    private String prefix;
    private int pointsToNextRank;
    private String nextRank;
    private String permission;
    private String group;
    private boolean isDefault = false;

    public Rank(String name, String id, String prefix, String pointsToNextRank, String nextRank, String permission, String group, boolean isDefault) {
        this.name = utils.formatColor(name);
        this.id = id;
        this.prefix = prefix;
        this.pointsToNextRank = Integer.parseInt(pointsToNextRank);
        this.nextRank = nextRank;
        this.permission = permission;
        this.group = group;
        this.isDefault = isDefault;
    }

    public Rank(String name, String id, String prefix, String pointsToNextRank, String nextRank, String permission, String group) {
        this.name = utils.formatColor(name);
        this.id = id;
        this.prefix = prefix;
        this.pointsToNextRank = Integer.parseInt(pointsToNextRank);
        this.nextRank = nextRank;
        this.permission = permission;
        this.group = group;
    }

    public Rank getNextRank() {
        return ConfigManager.getRank(nextRank);
    }

    public String getPrefix() {
        return utils.formatColor(prefix);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getPointsToNextRank() {
        return pointsToNextRank;
    }

    public String getPermission() {
        return permission;
    }

    public String getGroup() {
        return group;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
