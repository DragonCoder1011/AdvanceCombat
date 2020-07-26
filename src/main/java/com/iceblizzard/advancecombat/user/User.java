package com.iceblizzard.advancecombat.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private final String name;
    private final String uuid;

    public User(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public Player getUserAsPlayerByName() {
        return Bukkit.getPlayer(name);
    }

    public Player getPlayerByUUID() {
        return Bukkit.getPlayer(UUID.fromString(uuid));
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }
}
