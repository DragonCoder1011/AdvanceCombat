package com.iceblizzard.advancecombat.listener;

import com.iceblizzard.advancecombat.main.AdvanceCombat;
import com.iceblizzard.advancecombat.utils.CombatUtil;
import com.iceblizzard.advancecombat.utils.ConfigUtil;
import com.iceblizzard.advancecombat.utils.CoolDownUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class ChorusFruitListener implements Listener {

    private final ConfigUtil configUtil = ConfigUtil.getInstance();
    private final CombatUtil combatUtil = AdvanceCombat.getInstance().getCombatUtil();
    private final boolean disableChorusFruit = configUtil.getBoolean("disable-chorus-fruit");
    private final boolean isChorusFruitCooldown = configUtil.getBoolean("chorus-fruit-cooldown-enabled");

    @EventHandler
    public void onChorusFruit(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();
        ItemStack chrousFruit = e.getItem();
        if (chrousFruit.getType() == Material.CHORUS_FRUIT) {
            if (combatUtil.inCombat(player)) {
                if (disableChorusFruit) {
                    e.setCancelled(true);
                    return;
                }

                if (!isChorusFruitCooldown) {
                    return;
                }

                CoolDownUtils.getInstance().addToCoolDownForListener(player, "Chorus-Fruit", "chorus-fruit-cooldown-message",
                        e, "chorus-fruit-cooldown-delay");
            }
        }
    }
}

