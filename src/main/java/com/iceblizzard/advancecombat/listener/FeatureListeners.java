package com.iceblizzard.advancecombat.listener;

import com.iceblizzard.advancecombat.utils.ConfigUtil;
import com.iceblizzard.advancecombat.utils.FeaturesUtil;
import com.iceblizzard.advancecombat.utils.VersionUtils;
import hook.WorldGuardHook6_2;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

public class FeatureListeners implements Listener {

    private final ConfigUtil configUtil = ConfigUtil.getInstance();

    @EventHandler
    public void onKnockWG62(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (configUtil.getInteger("WorldGuardVersion") == 7) return;
        if (configUtil.getInteger("WorldGuardVersion") == 6) {
            if (e.getEntity() instanceof Player) {
                if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                    Player player = (Player) e.getEntity();
                    if (!configUtil.getBoolean("knockback-patch")) {
                        return;
                    }
                    if (WorldGuardHook6_2.isNotInPVP(player)) {
                        return;
                    } else {
                        FeaturesUtil.applyKnocbackVertical(player);
                        FeaturesUtil.applyKnocbackHorizontal(player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onKnockWG7(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (configUtil.getInteger("WorldGuardVersion") == 6) return;
        if (configUtil.getInteger("WorldGuardVersion") == 7) {
            if (e.getEntity() instanceof Player) {
                if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                    Player player = (Player) e.getEntity();
                    if (!configUtil.getBoolean("knockback-patch")) {
                        return;
                    }
                    if (WorldGuardHook6_2.isNotInPVP(player)) {
                        return;
                    } else {
                        FeaturesUtil.applyKnocbackVertical(player);
                        FeaturesUtil.applyKnocbackHorizontal(player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onVelocityChange(PlayerVelocityEvent e) {
        Player player = e.getPlayer();
        if (player.getLastDamageCause() == null) {
            return;
        }

        if (!(player.getLastDamageCause() instanceof EntityDamageByEntityEvent)) {
            return;
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        if (configUtil.getBoolean("fast-pot")) {
            FeaturesUtil.applySplashPotionVelocity(e.getEntity());
        }
    }

    @EventHandler
    public void onPotionIntensity(PotionSplashEvent e) {
        if (configUtil.getBoolean("fast-pot")) {
            FeaturesUtil.applySplashPotionIntensity(e);
        }
    }

    @EventHandler
    public void onSwapItems(PlayerSwapHandItemsEvent e){
        if(VersionUtils.isOldMC()) return;
        e.setCancelled(!configUtil.getBoolean("allow-swap-items"));
    }
}
