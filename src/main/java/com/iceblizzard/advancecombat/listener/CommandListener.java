package com.iceblizzard.advancecombat.listener;

import com.iceblizzard.advancecombat.main.AdvanceCombat;
import com.iceblizzard.advancecombat.utils.CombatUtil;
import com.iceblizzard.advancecombat.utils.ConfigUtil;
import com.iceblizzard.advancecombat.utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandListener implements Listener {

    private final ConfigUtil configUtil = ConfigUtil.getInstance();
    private final CombatUtil combatUtil = AdvanceCombat.getInstance().getCombatUtil();
    @EventHandler
    public void commandAction(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if (player.isOp()) {
            return;
        }

        if (!combatUtil.inCombat(player)) {
            return;
        }

        List<String> listedCommands = configUtil.getStringList("listed-commands");
        String[] executed = e.getMessage().split(" ");
        if (!configUtil.getBoolean("whitelist-mode")) {
            //Black List
            for (String commandword : listedCommands) {
                if (executed[0].equalsIgnoreCase("/" + commandword)) {
                    e.setCancelled(true);
                    player.sendMessage(StringUtils.format(configUtil.getString("prefix") + configUtil.getString("cant-do-command-message")));
                    break;
                }
            }
        } else {
            //Whitelist
            for (String commandword : listedCommands) {
                if (executed[0].equalsIgnoreCase("/" + commandword)) {
                    return;
                }
            }

            //Cancel Command
            e.setCancelled(true);
            player.sendMessage(StringUtils.format(configUtil.getString("prefix") + configUtil.getString("cant-do-command-message")));
        }
    }
}
