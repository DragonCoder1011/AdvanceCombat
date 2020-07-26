package com.iceblizzard.advancecombat.utils;

import org.bukkit.Bukkit;

public class VersionUtils {

    private static final String version = Bukkit.getServer().getVersion();

    public static boolean isOldMC() {
        return (version.contains("1.8"));
    }

    public static boolean isIsNewMC() {
        return (version.contains("1.13") || version.contains("1.13.2") || version.contains("1.14") || version.contains("1.15"));
    }

    //Used to register 1.9+ Listeners
    public static boolean is1_9Plus() {
        return (version.contains("1.9") || version.contains("1.10") ||
                version.contains("1.11") || version.contains("1.12") || version.contains("1.13") ||
                version.contains("1.14") || version.contains("1.15"));
    }
}
