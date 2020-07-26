package com.iceblizzard.advancecombat.listener;

import com.iceblizzard.advancecombat.main.AdvanceCombat;
import com.iceblizzard.advancecombat.utils.CombatUtil;
import com.iceblizzard.advancecombat.utils.ConfigUtil;
import com.iceblizzard.advancecombat.utils.CoolDownUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EnderpearlListener implements Listener {

    private final CombatUtil combatUtil = AdvanceCombat.getInstance().getCombatUtil();
    private final ConfigUtil configUtil = ConfigUtil.getInstance();
    private final boolean disableEnderPearls = configUtil.getBoolean("prevention-enderpearl-use");
    private final boolean isEnderPearlCooldown = configUtil.getBoolean("ender-pearl-cooldown-enabled");

    @EventHandler
    public void onEnderPearl(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack item = player.getItemInHand();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (item.getType() == Material.ENDER_PEARL) {
                if (combatUtil.inCombat(player)) {
                    if (disableEnderPearls) {
                        e.setCancelled(true);
                        return;
                    }

                    if (!isEnderPearlCooldown) {
                        return;
                    }
                    CoolDownUtils.getInstance().addToCoolDownForListener(player, "Ender-Pearl", "ender-pearl-cooldown-message",
                            e, "ender-pearl-cooldown-delay");
                }
            }
        }
    }
}
