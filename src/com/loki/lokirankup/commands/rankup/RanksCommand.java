package com.loki.lokirankup.commands.rankup;

import com.loki.lokirankup.components.Rank;
import com.loki.lokirankup.components.RankGroup;
import com.loki.lokirankup.components.RankUpPlayer;
import com.loki.lokirankup.managers.ConfigManager;
import com.loki.lokirankup.utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RanksCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        RankUpPlayer rankUpPlayer = ConfigManager.getPlayer(player.getUniqueId());
        Rank currentRank = rankUpPlayer.getRank();
        List<Rank> ranks = ConfigManager.getRanks();
        List<RankGroup> groups = ConfigManager.getRanksGroups();

        Inventory inventory = Bukkit.createInventory(null, 6*9, "§dRanks:");

        ItemStack paperInfo = new ItemStack(Material.PAPER);
        ItemMeta paperInfoMeta = paperInfo.getItemMeta();
        paperInfoMeta.setDisplayName("§dInformações");
        ArrayList<String> paperInfoLore = new ArrayList<>();

        paperInfoLore.add("§7Aqui você pode ver a");
        paperInfoLore.add("§7sua evolução e conhecer");
        paperInfoLore.add("§7os ranks disponíveis.");
        paperInfoLore.add("");
        paperInfoLore.add("§7Para evoluir você precisa de pontos,");
        paperInfoLore.add("§7que podem ser conseguidos");
        paperInfoLore.add("§7minerando na §f/mina §7do seu rank.");

        paperInfoMeta.setLore(paperInfoLore);
        paperInfo.setItemMeta(paperInfoMeta);

        for (RankGroup group : groups) {
            ItemStack item = new ItemStack(Material.BARRIER);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(group.getName());
            ArrayList<String> lore = new ArrayList<>();

            for (Rank rank : ranks) {
                if (rank.getGroup().equals(group.getId())) {

                    if (currentRank.equals(rank)) {
                        itemMeta.setDisplayName(rank.getName());
                        item = new ItemStack(group.getItem());
                        if(rank.getNextRank() == null) {
                            lore.add("");
                            lore.add("§7"+rank.getName()+" §7➩ §fNivel máximo");
                            lore.add(""+rankUpPlayer.getBar()+" §7("+ rankUpPlayer.getPoints() + "/" + rank.getPointsToNextRank() +")");
                        } else {
                            lore.add("");
                            lore.add("§7"+rank.getName()+" §7➩ " + rank.getNextRank().getName());
                            lore.add(""+rankUpPlayer.getBar()+" §7("+ rankUpPlayer.getPoints() + "/" + rank.getPointsToNextRank() +")");
                        }
                    } else {
                        if(rank.getNextRank() == null) {
                            lore.add("");
                            lore.add("§7"+rank.getName()+" §7➩ §fNivel máximo");
                            lore.add("§7▍▍▍▍▍▍▍▍▍▍▍▍▍▍▍"+" §7(0/" + rank.getPointsToNextRank() +")");
                        } else if (currentRank.getPointsToNextRank() > rank.getPointsToNextRank()) {
                            item = new ItemStack(group.getItem());
                            lore.add("");
                            lore.add("§7"+rank.getName()+" §7➩ " + rank.getNextRank().getName());
                            lore.add("§a▍▍▍▍▍▍▍▍▍▍▍▍▍▍▍ §7("+ rank.getPointsToNextRank() + "/" + rank.getPointsToNextRank() +")");

                        } else {
                            lore.add("");
                            lore.add("§7"+rank.getName()+" §7➩ " + rank.getNextRank().getName());
                            lore.add("§7▍▍▍▍▍▍▍▍▍▍▍▍▍▍▍ §7(0/" + rank.getPointsToNextRank() +")");
                        }
                    }
                }
            }

            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            inventory.setItem(group.getSlot(), item);
        }

        inventory.setItem(49, paperInfo);

        player.openInventory(inventory);

        return false;
    }
}
