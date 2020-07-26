package com.iceblizzard.advancecombat.user;

import com.google.common.collect.Maps;
import com.iceblizzard.advancecombat.main.AdvanceCombat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class UserManager {

    public AdvanceCombat plugin;
    private final Map<String, User> userMap = Maps.newConcurrentMap();

    public UserManager(AdvanceCombat plugin){
        this.plugin = plugin;
    }

    public void addUser(String name, String uuid) {
        if (containsUser(name)) return;
        userMap.put(name, new User(name, uuid));
    }

    public boolean containsUser(String name) {
        return userMap.containsKey(name);
    }

    public void removeUser(String name) {
        if (!containsUser(name)) return;
        userMap.remove(name);
    }

    public User getUser(String name) {
        if (!containsUser(name)) return null;
        return userMap.get(name);
    }

    public void addAllToUserMapIfAbsent() {
        if(Bukkit.getServer().getOnlinePlayers().size() == 0){
            return;
        }
        for (Player all : Bukkit.getServer().getOnlinePlayers()) {
            if (containsUser(all.getName())) return;
            addUser(all.getName(), all.getUniqueId().toString());
        }
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }
}
