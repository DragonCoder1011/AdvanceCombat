package com.iceblizzard.advancecombat.utils;

import com.iceblizzard.advancecombat.main.AdvanceCombat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class DisconnectUtils {

    private static final CombatUtil combatUtil = AdvanceCombat.getInstance().getCombatUtil();
    private static final ConfigUtil instance = ConfigUtil.getInstance();

    private static boolean isKickReasonIgnored(String string) {
        List<String> kickReasonIgnoreList = instance.getStringList("disconnect-reasons");
        return kickReasonIgnoreList.stream().anyMatch(string::contains);
    }

    public static void killPlayerOnKick(Player player, String reason) {
        if (isKickReasonIgnored(reason)) return;
        if (combatUtil.inCombat(player)) {
            if (instance.getBoolean("broadcast-combatlog")) {
                String prefix = instance.getString("prefix");
                String broadCast = instance.getString("broadcast-message");
                broadCast = broadCast.replace("%player%", player.getName());
                Bukkit.broadcastMessage(StringUtils.format(prefix + broadCast));
            }

            if (instance.getBoolean("logout-animation")) {
                player.getWorld().strikeLightningEffect(player.getLocation());
            }

            player.setHealth(0);
            combatUtil.removeFromCombat(player);
        }
    }

    public static void killPlayerOnQuit(Player player) {
        if (combatUtil.inCombat(player)) {
            if (instance.getBoolean("broadcast-combatlog")) {
                String prefix = instance.getString("prefix");
                String broadCast = instance.getString("broadcast-message");
                broadCast = broadCast.replace("%player%", player.getName());
                Bukkit.broadcastMessage(StringUtils.format(prefix + broadCast));
            }

            if (instance.getBoolean("logout-animation")) {
                player.getWorld().strikeLightningEffect(player.getLocation());
            }

            player.setHealth(0);
           combatUtil.removeFromCombat(player);
        }
    }
}
