package com.iceblizzard.advancecombat.listener;

import com.iceblizzard.advancecombat.main.AdvanceCombat;
import com.iceblizzard.advancecombat.utils.CombatUtil;
import com.iceblizzard.advancecombat.utils.ConfigUtil;
import com.iceblizzard.advancecombat.utils.FeaturesUtil;
import hook.WorldGuardHook6_2;
import hook.WorldGuardHook7;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class PlayerCombatListener implements Listener {

    private final CombatUtil combatUtil = AdvanceCombat.getInstance().getCombatUtil();
    private final ConfigUtil configUtil = ConfigUtil.getInstance();
    private final List<String> worldList = ConfigUtil.getInstance().getStringList("disabled-worlds");

    @EventHandler
    public void onTagWG6(EntityDamageByEntityEvent e) {
        if (!(e.getEntity().hasMetadata("real-player"))) return;
        if (configUtil.getInteger("WorldGuardVersion") == 7) return;
        if (configUtil.getInteger("WorldGuardVersion") == 6) {
            if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
                Player damager = (Player) e.getDamager();
                Player target = (Player) e.getEntity();
                for (String disabledWorlds : worldList) {
                    if (damager.getWorld().getName().equalsIgnoreCase(disabledWorlds) && target.getWorld().getName().equalsIgnoreCase(disabledWorlds)) {
                        return;
                    }
                }

                if (WorldGuardHook6_2.isNotInPVP(damager) || WorldGuardHook6_2.isNotInPVP(target)) {
                    return;
                } else {
                    FeaturesUtil.playBloodEffectPlayer(target);
                    combatUtil.combatTagDamagerByPlayer(damager, target);
                    combatUtil.combatTagTargetByPlayer(target, damager);
                    combatUtil.disableFlight(damager, target);
                }
            }
        }
    }


    @EventHandler
    public void onProjectileWG6(EntityDamageByEntityEvent e) {
        if (!(e.getEntity().hasMetadata("real-player"))) return;
        if (configUtil.getInteger("WorldGuardVersion") == 7) return;
        if (configUtil.getInteger("WorldGuardVersion") == 6) {
            if (e.getEntity() instanceof Player && e.getDamager() instanceof Projectile) {
                Projectile damager = (Projectile) e.getDamager();
                Player target = (Player) e.getEntity();
                if (!(damager.getShooter() instanceof Player)) {
                    return;
                }

                if (damager.getShooter() instanceof Player) {
                    Player shooter = (Player) damager.getShooter();
                    for (String disabledWorlds : worldList) {
                        if (shooter.getWorld().getName().equalsIgnoreCase(disabledWorlds) && target.getWorld().getName().equalsIgnoreCase(disabledWorlds)) {
                            return;
                        }
                    }

                    if (WorldGuardHook6_2.isNotInPVP(shooter) || WorldGuardHook6_2.isNotInPVP(target)) {
                        return;
                    } else {
                        FeaturesUtil.playBloodEffectPlayer(target);
                        combatUtil.combatTagDamagerByPlayer(shooter, target);
                        combatUtil.combatTagTargetByPlayer(target, shooter);
                        combatUtil.disableFlight(shooter, target);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onTagWG7(EntityDamageByEntityEvent e) {
        if (!(e.getEntity().hasMetadata("real-player"))) return;
        if (configUtil.getInteger("WorldGuardVersion") == 6) return;
        if (configUtil.getInteger("WorldGuardVersion") == 7) {
            if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
                Player damager = (Player) e.getDamager();
                Player target = (Player) e.getEntity();
                for (String disabledWorlds : worldList) {
                    if (damager.getWorld().getName().equalsIgnoreCase(disabledWorlds) && target.getWorld().getName().equalsIgnoreCase(disabledWorlds)) {
                        return;
                    }
                }

                if (WorldGuardHook7.isNotInPVP(damager) || WorldGuardHook7.isNotInPVP(target)) {
                    return;
                }
                FeaturesUtil.playBloodEffectPlayer(target);
                combatUtil.combatTagDamagerByPlayer(damager, target);
                combatUtil.combatTagTargetByPlayer(target, damager);
                combatUtil.disableFlight(damager, target);
            }
        }
    }

    @EventHandler
    public void onProjectileWG7(EntityDamageByEntityEvent e) {
        if (!(e.getEntity().hasMetadata("real-player"))) return;
        if (configUtil.getInteger("WorldGuardVersion") == 6) return;
        if (configUtil.getInteger("WorldGuardVersion") == 7) {
            if (e.getEntity() instanceof Player && e.getDamager() instanceof Projectile) {
                Projectile damager = (Projectile) e.getDamager();
                Player target = (Player) e.getEntity();
                if (!(damager.getShooter() instanceof Player)) {
                    return;
                }

                if (damager.getShooter() instanceof Player) {
                    Player shooter = (Player) damager.getShooter();
                    for (String disabledWorlds : worldList) {
                        if (shooter.getWorld().getName().equalsIgnoreCase(disabledWorlds) && target.getWorld().getName().equalsIgnoreCase(disabledWorlds)) {
                            return;
                        }
                    }

                    if (WorldGuardHook7.isNotInPVP(shooter) || WorldGuardHook7.isNotInPVP(target)) {
                        return;
                    }
                    FeaturesUtil.playBloodEffectPlayer(target);
                    combatUtil.combatTagDamagerByPlayer(shooter, target);
                    combatUtil.combatTagTargetByPlayer(target, shooter);
                    combatUtil.disableFlight(shooter, target);
                }
            }
        }
    }
}
