package com.iceblizzard.advancecombat.user;

import com.iceblizzard.advancecombat.main.AdvanceCombat;
import org.bukkit.entity.Player;

public class UserObjects {

    private final UserManager userManager = AdvanceCombat.getInstance().getUserManager();
    private int ping;

    public UserObjects() {

    }

    public String getName(Player player) {
        return userManager.getUser(player.getName()).getName();
    }

    /*

    @Used to send multiple messages

   */
    public void sendMessages(Player player, String[] messages) {
       userManager.getUser(player.getName()).getUserAsPlayerByName().sendMessage(messages);
    }

    public synchronized int getPing(Player player) {
        try {
            Object entityPlayer = userManager.getUser(player.getName()).getUserAsPlayerByName().getClass().getMethod("getHandle").invoke(player);
            ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ping;
    }
}
