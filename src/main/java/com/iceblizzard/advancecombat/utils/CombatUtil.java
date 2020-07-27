package com.iceblizzard.advancecombat.utils;

import com.iceblizzard.advancecombat.main.AdvanceCombat;
import com.iceblizzard.advancecombat.task.CombatTimerTask;
import com.iceblizzard.advancecombat.user.User;
import com.iceblizzard.advancecombat.user.UserManager;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class CombatUtil {

    public AdvanceCombat plugin;
    private final Map<User, Long> taggedList = Maps.newConcurrentMap();
    private final UserManager userManager = AdvanceCombat.getInstance().getUserManager();
    private static final ConfigUtil configUtil = ConfigUtil.getInstance();
    private final String prefix = configUtil.getString("prefix");
    private final long cooldown = configUtil.getInteger("combat-timer");

    public CombatUtil(AdvanceCombat plugin) {
        this.plugin = plugin;
    }

    /*

     @Applied When The Player Tags A Player

     */
    public void combatTagDamagerByPlayer(Player damager, Player target) {
        if (!taggedList.containsKey(userManager.getUser(damager.getName()))) {
            String entered = configUtil.getString("damager-combat-message");
            entered = entered.replace("%player%", target.getName());
            entered = entered.replace("%time%", String.valueOf(cooldown));
            damager.sendMessage(StringUtils.format(prefix + entered));
            taggedList.put(userManager.getUser(damager.getName()), System.currentTimeMillis() + (cooldown * 1000));
        }

        if (taggedList.containsKey(userManager.getUser(damager.getName()))) {
            taggedList.remove(userManager.getUser(damager.getName()));
            taggedList.put(userManager.getUser(damager.getName()), System.currentTimeMillis() + (cooldown * 1000));
        }
    }

    /*

    @Applied When The Player Damages An Entity

     */

    public void combatTagDamagerByEntity(Player damager, LivingEntity entity) {
        List<String> mobs = configUtil.getStringList("mob-list");
        for (String entities : mobs) {
            EntityType type = entity.getType();
            if (type == EntityType.valueOf(entities)) {
                if (type == EntityType.valueOf(entities) && taggedList.containsKey(userManager.getUser(damager.getName()))) {
                    taggedList.remove(userManager.getUser(damager.getName()));
                    taggedList.put(userManager.getUser(damager.getName()), System.currentTimeMillis() + (cooldown * 1000));
                }

                if (!taggedList.containsKey(userManager.getUser(damager.getName()))) {
                    String entered = configUtil.getString("damager-combat-entity-message");
                    entered = entered.replace("%entity%", String.valueOf(entity.getType().toString()));
                    entered = entered.replace("%time%", String.valueOf(cooldown));
                    damager.sendMessage(StringUtils.format(prefix + entered));
                    taggedList.put(userManager.getUser(damager.getName()), System.currentTimeMillis() + (cooldown * 1000));
                }
            }
        }
    }


    /*

    @Applied When The Player Gets Tagged By A Player

    */
    public void combatTagTargetByPlayer(Player target, Player damager) {
        if (!taggedList.containsKey(userManager.getUser(target.getName()))) {
            String entered = configUtil.getString("target-combat-message");
            entered = entered.replace("%player%", String.valueOf(damager.getName()));
            entered = entered.replace("%time%", String.valueOf(cooldown));
            target.sendMessage(StringUtils.format(prefix + entered));
            taggedList.put(userManager.getUser(target.getName()), System.currentTimeMillis() + (cooldown * 1000));
        }

        if (taggedList.containsKey(userManager.getUser(target.getName()))) {
            taggedList.remove(userManager.getUser(target.getName()));
            taggedList.put(userManager.getUser(target.getName()), System.currentTimeMillis() + (cooldown * 1000));
        }
    }

    /*

   @Applied When An Entity Damages The Player

    */
    public void combatTagTargetByEntity(Player target, LivingEntity entity) {
        List<String> mobs = configUtil.getStringList("mob-list");
        for (String entities : mobs) {
            EntityType type = entity.getType();
            if (type == EntityType.valueOf(entities)) {
                if (type == EntityType.valueOf(entities) && taggedList.containsKey(userManager.getUser(target.getName()))) {
                    taggedList.remove(userManager.getUser(target.getName()));
                    taggedList.put(userManager.getUser(target.getName()), System.currentTimeMillis() + (cooldown * 1000));
                }

                if (!taggedList.containsKey(userManager.getUser(target.getName()))) {
                    String entered = configUtil.getString("entity-combat-damager-message");
                    entered = entered.replace("%entity%", String.valueOf(entity.getType().toString()));
                    entered = entered.replace("%time%", String.valueOf(cooldown));
                    target.sendMessage(StringUtils.format(prefix + entered));
                    taggedList.put(userManager.getUser(target.getName()), System.currentTimeMillis() + (cooldown * 1000));
                }
            }
        }
    }

    /*

    @Used to disable flight for both the damager and target

     */

    public void disableFlight(Player damager, Player target) {
        damager.setAllowFlight(false);
        damager.setFlying(false);
        target.setAllowFlight(false);
        target.setFlying(false);
    }

    /*

    @Gets the combat time of the player

     */
    public void getTime(Player player) {
        if (inCombat(player) && taggedList.get(userManager.getUser(player.getName())) >= System.currentTimeMillis()) {
            long remainingTime = taggedList.get(userManager.getUser(player.getName())) - System.currentTimeMillis();
            String inCombatMessage = configUtil.getString("in-combat-message");
            inCombatMessage = inCombatMessage.replace("%time%", String.valueOf(remainingTime / 1000));
            player.sendMessage(StringUtils.format(prefix + inCombatMessage));
        }

        if (!inCombat(player)) {
            String notInCombatMessage = configUtil.getString("not-in-combat-message");
            player.sendMessage(StringUtils.format(prefix + notInCombatMessage));
        }
    }

    /*

    @Untags a player

     */
    public void untag(Player player, Player commandPlayer) {
        if (!inCombat(player)) {
            String playerNotInCombatMessage = configUtil.getString("player-not-in-combat-message");
            commandPlayer.sendMessage(StringUtils.format(prefix + playerNotInCombatMessage));
        }

        if (inCombat(player)) {
            removeFromCombat(player);
            String untaggedPlayerMessage = configUtil.getString("untagged-player-message");
            untaggedPlayerMessage = untaggedPlayerMessage.replace("%player%", player.getName());
            commandPlayer.sendMessage(StringUtils.format(prefix + untaggedPlayerMessage));
            String targetUntaggedMessage = configUtil.getString("target-untagged-message");
            player.sendMessage(StringUtils.format(prefix + targetUntaggedMessage));
        }
    }


    /*

    @Remove the player if they're in combat

     */
    public void removeFromCombat(Player player) {
        taggedList.remove(userManager.getUser(player.getName()));
    }

    /*

    @Checks if the player is in combat

     */
    public boolean inCombat(Player player) {
        return taggedList.containsKey(userManager.getUser(player.getName()));
    }

    /*

    @Global timer for the combat player

     */
    public void combatTimer() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(AdvanceCombat.getPlugin(AdvanceCombat.class), new CombatTimerTask(), 20, 20);
    }

    /*

    @Combats get cleared on reload. Prevents players from losing their items if in combat

     */
    public void clearCombats() {
        if (taggedList.isEmpty()) {
            return;
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (inCombat(all) && taggedList.get(userManager.getUser(all.getName())) > System.currentTimeMillis()) {
                taggedList.clear();
            }
        }
    }

    /*

    @Checks if the player is in a world where combat exists or not

     */
    public boolean isinWorld(Player player) {
        List<String> worlds = configUtil.getStringList("disabled-worlds");
        for (String allWorlds : worlds) {
            if (player.getWorld().getName().equalsIgnoreCase(allWorlds)) {
                return true;
            }
        }

        return false;
    }

    /*

    @Gets the list of players in combat

     */
    public void printCombatInfo(Player commandPlayer) {
        if (taggedList.isEmpty()) {
            commandPlayer.sendMessage(StringUtils.format(prefix + configUtil.getString("player-list-empty-message")));
            return;
        }

        for (User tagged : taggedList.keySet()) {
            String message = configUtil.getString("players-in-combat-list-messages");
            message = message.replace("%player%", userManager.getUser(tagged.getName()).getName());
            commandPlayer.sendMessage(StringUtils.format(prefix + message));
        }
    }

    public Map<User, Long> getTaggedList() {
        return taggedList;
    }
}
