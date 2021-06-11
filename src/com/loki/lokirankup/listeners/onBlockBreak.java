package com.loki.lokirankup.listeners;

import com.loki.lokirankup.main;
import com.loki.lokirankup.utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class onBlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        World world = Bukkit.getServer().getWorld("world");
        Location loc1 = new Location(world, 51, 1, -1);
        Location loc2 = new Location(world, 66, 10, 12);

        List<Block> blocks = utils.blocksFromTwoPoints(loc1, loc2);

        if (!blocks.contains(block) && block != null) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player target: Bukkit.getOnlinePlayers()) {
                    target.playSound(block.getLocation(), Sound.CHICKEN_EGG_POP, 30, 20);
                }
                block.setType(Material.DIRT);
            }
        }.runTaskLater(main.plugin, 1000*5);

        /*
        for (Block blockInRegion : blocks) {
            if (blockInRegion.getType().equals(Material.AIR)) {
                player.sendMessage("ok");
                blockInRegion.setType(Material.DIRT);
            }
        }
         */
    }
}
