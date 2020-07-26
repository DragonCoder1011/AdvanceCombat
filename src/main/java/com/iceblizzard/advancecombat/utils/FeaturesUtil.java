package com.iceblizzard.advancecombat.utils;

import com.iceblizzard.advancecombat.main.AdvanceCombat;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class FeaturesUtil {

    private static final CombatUtil combatUtil = AdvanceCombat.getInstance().getCombatUtil();
    private static final ConfigUtil configUtil = ConfigUtil.getInstance();
    private static final double splashSpeed = configUtil.getDouble("splash-pot-speed");

    public static void playBloodEffectPlayer(Player player) {
        if (configUtil.getBoolean("blood-effect-player") && !combatUtil.inCombat(player)) {
            player.getWorld().playEffect(player.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        }
    }

    public static void playBloodEffectEntity(Player player, LivingEntity entity) {
        if (!configUtil.getBoolean("enable-mob-combat")) {
            return;
        }
        List<String> mobs = configUtil.getStringList("mob-list");
        for (String entities : mobs) {
            EntityType type = entity.getType();
            if (type == EntityType.valueOf(entities)) {
                if (configUtil.getBoolean("blood-effect-player") && !combatUtil.inCombat(player)) {
                    player.getWorld().playEffect(player.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
                }
            }
        }
    }

    public static void applySplashPotionVelocity(Entity entity) {
        if (entity.getType() == EntityType.SPLASH_POTION) {
            Projectile projectile = (Projectile) entity;
            if (projectile.getShooter() instanceof Player && ((Player) projectile.getShooter()).isSprinting()) {
                Vector vector = projectile.getVelocity();
                vector.setY(vector.getY() - splashSpeed);
                projectile.setVelocity(vector);
            }
        }
    }

    public static void applySplashPotionIntensity(PotionSplashEvent e) {
        if (e.getEntity().getShooter() instanceof Player) {
            Player player = (Player) e.getEntity().getShooter();
            if (player.isSprinting() && e.getIntensity(player) > 0.5) {
                e.setIntensity(player, 1.0D);
            }
        }
    }

    public static void applyKnocbackVertical(Player player) {
        double vertical = configUtil.getDouble("knockback.vertical");
        double finalResult = -player.getVelocity().getY() + vertical;
        if (finalResult >= 0.05 || finalResult <= 0) {
            finalResult = vertical;
        }

        player.setVelocity(player.getVelocity().setY(finalResult));
        if (configUtil.getBoolean("debug")) {
            player.sendMessage("Vertical Level: " + finalResult);
        }
    }

    public static void applyKnocbackHorizontal(Player player) {
        double horizontal = configUtil.getDouble("knockback.horizontal");
        double finalResultX = -player.getVelocity().getX() + horizontal;
        double finalResultZ = -player.getVelocity().getZ() + horizontal;
        if (finalResultX >= 0.05 || finalResultX <= 0) {
            finalResultX = horizontal;
        }

        if (finalResultZ >= 0.05 || finalResultZ <= 0) {
            finalResultZ = horizontal;
        }

        player.setVelocity(player.getVelocity().setX(finalResultX));
        player.setVelocity(player.getVelocity().setZ(finalResultZ));

        if (configUtil.getBoolean("debug")) {
            player.sendMessage("X Level: " + finalResultX);
            player.sendMessage("Z Level: " + finalResultZ);
        }
    }
}
