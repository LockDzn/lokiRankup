package com.loki.lokirankup.commands.rankup;

import com.loki.lokirankup.utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ResetMineCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        World world = Bukkit.getServer().getWorld("world");
        Location loc1 = new Location(world, 51, 1, -2);
        Location loc2 = new Location(world, 66, 10, 12);

        List<Block> blocks = utils.blocksFromTwoPoints(loc1, loc2);

        for (Block blockInRegion : blocks) {
            if (blockInRegion.getType().equals(Material.AIR)) {
                blockInRegion.setType(Material.DIRT);
            }
        }

        commandSender.sendMessage("§Minas resetadas com sucesso.");
        return false;
    }
}
