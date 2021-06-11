package com.loki.lokirankup.components;

import com.loki.lokirankup.utils;
import org.bukkit.Material;

public class RankGroup {
    private String id;
    private String name;
    private Material item;
    private int slot;

    public RankGroup(String name, String id, String item, String slot) {
        this.id = id;
        this.name = utils.formatColor(name);
        this.item = Material.matchMaterial(item);
        this.slot = Integer.parseInt(slot);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Material getItem() {
        return item;
    }

    public int getSlot() {
        return slot-1;
    }
}
