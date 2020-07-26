package hook;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldGuardHook7 {


    private static WorldGuardPlugin getWGPlugin(){
        return JavaPlugin.getPlugin(WorldGuardPlugin.class);
    }

    public static boolean isNotInPVP(Player player) {
        LocalPlayer localPlayer = getWGPlugin().wrapPlayer(player);
        Location location = BukkitAdapter.adapt(player.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet ar = query.getApplicableRegions(location);
        return ar.queryState(localPlayer, Flags.PVP) == StateFlag.State.DENY;
    }

}
