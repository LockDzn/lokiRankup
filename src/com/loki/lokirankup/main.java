package com.loki.lokirankup;

import com.loki.lokirankup.commands.*;
import com.loki.lokirankup.commands.chat.ChatCommand;
import com.loki.lokirankup.commands.chat.GlobalChatCommand;
import com.loki.lokirankup.commands.home.CreateHomeCommand;
import com.loki.lokirankup.commands.home.DeleteHomeCommand;
import com.loki.lokirankup.commands.home.HomeCommand;
import com.loki.lokirankup.commands.home.SetPublicHomeCommand;
import com.loki.lokirankup.commands.rankup.RanksCommand;
import com.loki.lokirankup.commands.rankup.ReloadCommand;
import com.loki.lokirankup.commands.rankup.ResetMineCommand;
import com.loki.lokirankup.components.ConfigComponents;
import com.loki.lokirankup.listeners.onBlockBreak;
import com.loki.lokirankup.listeners.onChat;
import com.loki.lokirankup.listeners.onInventoryClick;
import com.loki.lokirankup.listeners.onJoin;
import com.loki.lokirankup.managers.ConfigManager;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

    public static main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getConsoleSender().sendMessage("§a[lokiRankup] Iniciado.");

        registerListeners();
        registerCommand();

        ConfigManager.start();
        scoreboardAPI.start();

        ConfigComponents.ConfigObject configObject = ConfigManager.getConfig();
        MinecraftServer.getServer().setMotd(utils.formatColor(configObject.motd));
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§c[lokiRankup] Desativado.");
    }

    public void registerCommand() {
        getCommand("ranks").setExecutor(new RanksCommand());
        getCommand("rankupreload").setExecutor(new ReloadCommand());
        getCommand("resetmine").setExecutor(new ResetMineCommand());
        getCommand("g").setExecutor(new GlobalChatCommand());
        getCommand("chat").setExecutor(new ChatCommand());
        getCommand("npc").setExecutor(new NPCCommand());

        getCommand("home").setExecutor(new HomeCommand());
        getCommand("sethome").setExecutor(new CreateHomeCommand());
        getCommand("publica").setExecutor(new SetPublicHomeCommand());
        getCommand("delhome").setExecutor(new DeleteHomeCommand());
    }

    public void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new onJoin(), this);
        pm.registerEvents(new onBlockBreak(), this);
        pm.registerEvents(new onInventoryClick(), this);
        pm.registerEvents(new onChat(), this);
    }
}
