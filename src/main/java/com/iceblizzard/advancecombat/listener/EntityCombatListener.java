package com.iceblizzard.advancecombat.listener;

import com.iceblizzard.advancecombat.main.AdvanceCombat;
import com.iceblizzard.advancecombat.utils.CombatUtil;
import com.iceblizzard.advancecombat.utils.ConfigUtil;
import com.iceblizzard.advancecombat.utils.FeaturesUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerRespawnEvent;



public class EntityCombatListener implements Listener {

    private final CombatUtil combatUtil = AdvanceCombat.getInstance().getCombatUtil();
    private final ConfigUtil configUtil = ConfigUtil.getInstance();


    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!configUtil.getBoolean("enable-mob-combat")) {
            return;
        }
        /*
         @Triggered when a mob hits a player.
         */
        if (e.getEntity() instanceof Player && e.getDamager() instanceof LivingEntity) {
            Player target = (Player) e.getEntity();
            LivingEntity damager = (LivingEntity) e.getDamager();
            combatUtil.checkWorlds(target);
            FeaturesUtil.playBloodEffectEntity(target, damager);
            combatUtil.combatTagTargetByEntity(target, damager);
        }
        /*
         @Triggered when a projectile source from a mob hits a player.
         IE. Arrow, Trident, Crossbows, etc.
         */
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Projectile) {
            Projectile damager = (Projectile) e.getDamager();
            Player target = (Player) e.getEntity();
            if (damager instanceof Player) {
                return;
            }
            LivingEntity shooter = (LivingEntity) damager.getShooter();
            combatUtil.checkWorlds(target);
            FeaturesUtil.playBloodEffectEntity(target, shooter);
            combatUtil.combatTagTargetByEntity(target, shooter);
        }
        /*
         @Triggered when a player hits a mob.
         */
        if (e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            LivingEntity target = (LivingEntity) e.getEntity();
            combatUtil.checkWorlds(damager);
            combatUtil.combatTagDamagerByEntity(damager, target);
        }
        /*
        @Triggered when a player shoots a mob.
         */
        if (e.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) e.getDamager();
            if (!(projectile.getShooter() instanceof Player)) {
                return;
            }

            if (projectile.getShooter() instanceof Player) {
                Player shooter = (Player) projectile.getShooter();
                LivingEntity target = (LivingEntity) e.getEntity();
                combatUtil.checkWorlds(shooter);
                combatUtil.combatTagDamagerByEntity(shooter, target);
            }
        }
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (combatUtil.inCombat(player)) {
            combatUtil.removeFromCombat(player);
        }
    }

    @EventHandler
    public void finalCheck(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        if (combatUtil.inCombat(player)) {
            combatUtil.removeFromCombat(player);
        }
    }
}