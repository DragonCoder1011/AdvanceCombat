package hook;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.bukkit.entity.Player;

public class WorldGuardHook6_2 {

    public static boolean isNotInPVP(Player player) {
        ApplicableRegionSet ar = WorldGuardPlugin.inst().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
        LocalPlayer lp = WorldGuardPlugin.inst().wrapPlayer(player);
        return ar.queryState(lp, DefaultFlag.PVP) == StateFlag.State.DENY;
    }
}

