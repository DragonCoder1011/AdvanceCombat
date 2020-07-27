package com.iceblizzard.advancecombat.utils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.iceblizzard.advancecombat.main.AdvanceCombat;
import com.iceblizzard.advancecombat.user.User;
import com.iceblizzard.advancecombat.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class CoolDownUtils {

    private static CoolDownUtils instance = null;
    private final Table<User, String, Long> cooldowns = HashBasedTable.create();
    private final UserManager userManager = AdvanceCombat.getInstance().getUserManager();
    private final ConfigUtil configUtil = ConfigUtil.getInstance();

    public static CoolDownUtils getInstance() {
        if (instance == null) instance = new CoolDownUtils();
        return instance;
    }

    private boolean containsCooldown(Player player, String key) {
        return cooldowns.contains(userManager.getUser(player.getName()), key);
    }

    private long getCooldown(Player player, String key) {
        return cooldowns.get(userManager.getUser(player.getName()), key);
    }


    private void setCooldown(Player player, String key, long delay) {
        cooldowns.put(userManager.getUser(player.getName()), key, System.currentTimeMillis() + (TimeUnitUtil.secondToMilli(delay)));
    }


    private void removeCooldown(Player player, String key) {
        cooldowns.remove(userManager.getUser(player.getName()), key);
    }

    public void removeCooldowns(Player player) {
        cooldowns.row(userManager.getUser(player.getName())).clear();
    }


    public void addToCoolDownForListener(Player player, String cooldownKey, String path, Cancellable event, String coolDownDelay) {

        if (containsCooldown(player, cooldownKey) && getCooldown(player, cooldownKey) > System.currentTimeMillis()) {
            long remainingTime = getCooldown(player, cooldownKey) - System.currentTimeMillis();
            String prefix = configUtil.getString("prefix");
            String message = configUtil.getString(path);
            message = message.replace("%time%", String.valueOf(TimeUnitUtil.milliToSecond(remainingTime)));
            player.sendMessage(StringUtils.format(prefix + message));
            event.setCancelled(true);
        } else {
            long coolDown = configUtil.getInteger(coolDownDelay);
            removeCooldown(player, cooldownKey);
            setCooldown(player, cooldownKey, coolDown);
        }
    }
}