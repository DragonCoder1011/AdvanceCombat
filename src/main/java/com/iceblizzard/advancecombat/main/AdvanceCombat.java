package com.iceblizzard.advancecombat.main;

import com.iceblizzard.advancecombat.listener.*;
import com.iceblizzard.advancecombat.packets.ActionBar;
import com.iceblizzard.advancecombat.task.AddUserTask;
import com.iceblizzard.advancecombat.user.UserManager;
import com.iceblizzard.advancecombat.utils.CombatUtil;
import com.iceblizzard.advancecombat.utils.VersionUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class AdvanceCombat extends JavaPlugin {


    private UserManager userManager;
    private CombatUtil combatUtil;

    public void onEnable() {
        userManager = new UserManager(this);
        combatUtil = new CombatUtil(this);
        ActionBar.register();
        registerCommands();
        registerConfig();
        registerListeners();
        callTasks();

    }

    public void onDisable() {
        combatUtil.clearCombats();
    }

    private void registerCommands() {

    }

    private void registerListeners() {
        addListener(new EntityCombatListener(),
                new ConnectionListeners(),
                new CommandListener(),
                new EnderpearlListener(),
                new PlayerCombatListener());

        if (VersionUtils.is1_9Plus()) {
            addListener(new ChorusFruitListener());
        }

        if (VersionUtils.isIsNewMC()) {
            addListener(new TotemofUndyingListener());
        }
    }

    private void addCommand(String name, BukkitCommand bc) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(name, bc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addListener(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    private void callTasks() {
        combatUtil.combatTimer();
        Bukkit.getScheduler().runTaskLater(this, new AddUserTask(), 1);
    }

    public static AdvanceCombat getInstance(){
        return AdvanceCombat.getPlugin(AdvanceCombat.class);
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public CombatUtil getCombatUtil() {
        return combatUtil;
    }
}
