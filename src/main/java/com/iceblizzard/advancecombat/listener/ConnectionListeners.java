package com.iceblizzard.advancecombat.listener;

import com.iceblizzard.advancecombat.utils.CoolDownUtils;
import com.iceblizzard.advancecombat.main.AdvanceCombat;
import com.iceblizzard.advancecombat.user.UserManager;
import com.iceblizzard.advancecombat.utils.ConfigUtil;
import com.iceblizzard.advancecombat.utils.DisconnectUtils;
import com.iceblizzard.advancecombat.utils.VersionUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Objects;

public class ConnectionListeners implements Listener {

    private final UserManager userManager = AdvanceCombat.getInstance().getUserManager();

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!player.hasMetadata("real-player")) {
            player.setMetadata("real-player", new FixedMetadataValue(AdvanceCombat.getPlugin(AdvanceCombat.class), true));
        }

        userManager.addUser(player.getName(), player.getUniqueId().toString());

        if (VersionUtils.is1_9Plus()) {
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)).setBaseValue(ConfigUtil.getInstance().getDouble("hit-speed"));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onKick(PlayerKickEvent e) {
        Player player = e.getPlayer();
        DisconnectUtils.killPlayerOnKick(player, e.getReason());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        DisconnectUtils.killPlayerOnQuit(player);
        clearCooldowns(player);
        userManager.removeUser(player.getName());
    }

    private void clearCooldowns(Player player) {
        CoolDownUtils.getInstance().removeCooldowns(player);
    }
}
