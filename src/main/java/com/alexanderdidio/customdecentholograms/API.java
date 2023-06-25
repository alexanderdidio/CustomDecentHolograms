package com.alexanderdidio.customdecentholograms;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.iridium.iridiumskyblock.PermissionType;
import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.database.Island;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import com.plotsquared.core.plot.Plot;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.lists.Flags;
import world.bentobox.bentobox.managers.IslandsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class API {

    public static boolean checkLocation(Player player) {
        switch (CustomDecentHolograms.api.toLowerCase()) {
            case "griefprevention":
                return griefPrevention(player);
            case "plotsquared":
                return plotSquared(player);
            case "towny":
                return towny(player);
            case "bentobox":
                return bentoBox(player);
            case "superiorskyblock":
                return superiorSkyblock(player);
            case "iridium":
                return iridium(player);
            default:
                return false;
        }
    }

    public static boolean validateAPI() {
        String apiConfig = CustomDecentHolograms.api;
        List<String> apis = new ArrayList<>();
        apis.add("griefprevention");
        apis.add("plotsquared");
        apis.add("towny");
        apis.add("bentobox");
        apis.add("superiorskyblock");
        apis.add("iridium");
        for (String api : apis) {
            if (api.equalsIgnoreCase(apiConfig)) {
                return false;
            }
        }
        return true;
    }

    private static boolean griefPrevention(Player player) {
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(player.getLocation(), false, false, null);
        return claim != null && claim.hasExplicitPermission(player, ClaimPermission.Build);
    }

    private static boolean plotSquared(Player player) {
        Location location = player.getLocation();
        String world = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        Plot plot = Plot.getPlot(com.plotsquared.core.location.Location.at(world, x, y, z));
        return plot != null && plot.getTrusted().contains(player.getUniqueId());
    }

    private static boolean towny(Player player) {
        Location location = player.getLocation();
        return PlayerCacheUtil.getCachePermission(player, location, Material.ARMOR_STAND, TownyPermission.ActionType.BUILD);
    }

    private static boolean bentoBox(Player player) {
        IslandsManager manager = BentoBox.getInstance().getIslandsManager();
        Optional<world.bentobox.bentobox.database.objects.Island> island = manager.getIslandAt(player.getLocation());
        return island.isPresent() && island.get().isAllowed(User.getInstance(player), Flags.PLACE_BLOCKS);
    }

    private static boolean superiorSkyblock(Player player) {
        Location location = player.getLocation();
        SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player);
        com.bgsoftware.superiorskyblock.api.island.Island island = SuperiorSkyblockAPI.getIslandAt(location);
        return island != null && (island.isMember(superiorPlayer) || island.isCoop(superiorPlayer));
    }

    private static boolean iridium(Player player) {
        Optional<Island> island = IridiumSkyblockAPI.getInstance().getIslandViaLocation(player.getLocation());
        com.iridium.iridiumskyblock.database.User user = IridiumSkyblockAPI.getInstance().getUser(player);
        return island.isPresent() && IridiumSkyblockAPI.getInstance().getIslandPermission(island.get(), user, PermissionType.BLOCK_BREAK);
    }
}
