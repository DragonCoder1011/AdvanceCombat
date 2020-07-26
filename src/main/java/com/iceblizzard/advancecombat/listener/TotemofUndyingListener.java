package com.iceblizzard.advancecombat.listener;

import com.iceblizzard.advancecombat.main.AdvanceCombat;
import com.iceblizzard.advancecombat.utils.CombatUtil;
import com.iceblizzard.advancecombat.utils.ConfigUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;

public class TotemofUndyingListener implements Listener {

    private final CombatUtil combatUtil = AdvanceCombat.getInstance().getCombatUtil();

    @EventHandler
    private void totemOfUndying(EntityResurrectEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getEntity();
        if (combatUtil.inCombat(player)) {
            if (ConfigUtil.getInstance().getBoolean("prevention-totem-of-undying")) {
                e.setCancelled(true);
            }
        }
    }
}
