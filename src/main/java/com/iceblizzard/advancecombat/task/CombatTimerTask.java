package com.iceblizzard.advancecombat.task;

import com.iceblizzard.advancecombat.main.AdvanceCombat;
import com.iceblizzard.advancecombat.packets.ActionBar;
import com.iceblizzard.advancecombat.user.UserManager;
import com.iceblizzard.advancecombat.utils.CombatUtil;
import com.iceblizzard.advancecombat.utils.ConfigUtil;
import com.iceblizzard.advancecombat.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CombatTimerTask implements Runnable {

    private final CombatUtil combatUtil = AdvanceCombat.getInstance().getCombatUtil();
    private final UserManager userManager = AdvanceCombat.getInstance().getUserManager();
    private final ConfigUtil configUtil = ConfigUtil.getInstance();
    private final String prefix = configUtil.getString("prefix");

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (combatUtil.inCombat(player) && combatUtil.getTaggedList().get(userManager.getUser(player.getName())) > System.currentTimeMillis()) {
                long remainingTime = combatUtil.getTaggedList().get(userManager.getUser(player.getName())) - System.currentTimeMillis();
                String actionBarMessage = configUtil.getString("actionbar-message");
                actionBarMessage = actionBarMessage.replace("%time%", String.valueOf(remainingTime / 1000));
                ActionBar.sendActionBar(player, StringUtils.format(actionBarMessage));
            }
            if (combatUtil.inCombat(player) && combatUtil.getTaggedList().get(userManager.getUser(player.getName())) < System.currentTimeMillis()) {
                combatUtil.removeFromCombat(player);
                String notInCombatMessage = configUtil.getString("not-in-combat-message");
                player.sendMessage(StringUtils.format(prefix + notInCombatMessage));
            }
        }
    }
}
