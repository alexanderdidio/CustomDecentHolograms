package com.alexanderdidio.customdecentholograms.utils;

import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.griefdefender.api.Core;
import com.griefdefender.api.GriefDefender;
import com.iridium.iridiumskyblock.PermissionType;
import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.database.Island;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import com.plotsquared.core.plot.Plot;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.land.Area;
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
import java.util.UUID;

public class API {
    private final CustomDecentHolograms plugin;

    public API(CustomDecentHolograms plugin) {
        this.plugin = plugin;
    }

    public boolean checkLocation(Player player) {
        switch (plugin.getAPIConfig().toLowerCase()) {
            case "griefprevention":
                return getGriefPrevention(player);
            case "griefdefender":
                return getGriefDefender(player);
            case "plotsquared":
                return getPlotSquared(player);
            case "worldguard":
                return getWorldGuard(player);
            case "towny":
                return getTowny(player);
            case "lands":
                return getLands(player);
            case "bentobox":
                return getBentoBox(player);
            case "superiorskyblock":
                return getSuperiorSkyblock(player);
            case "iridiumskyblock":
                return getIridium(player);
            default:
                return false;
        }
    }

    public boolean validateAPI() {
        String apiConfig = plugin.getAPIConfig();
        List<String> apis = new ArrayList<>();
        apis.add("griefprevention");
        apis.add("griefdefender");
        apis.add("plotsquared");
        apis.add("worldguard");
        apis.add("towny");
        apis.add("lands");
        apis.add("bentobox");
        apis.add("superiorskyblock");
        apis.add("iridiumskyblock");
        for (String api : apis) {
            if (api.equalsIgnoreCase(apiConfig)) {
                return true;
            }
        }
        return false;
    }

    private boolean getGriefPrevention(Player player) {
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(player.getLocation(), false, false, null);
        return claim != null && claim.hasExplicitPermission(player, ClaimPermission.Build);
    }

    private boolean getGriefDefender(Player player) {
        Location location = player.getLocation();
        Core core = GriefDefender.getCore();
        com.griefdefender.api.claim.Claim claim = core.getClaimAt(location);
        com.griefdefender.api.User user = core.getUser(player.getUniqueId());
        if (claim == null) {
            return false;
        }
        if (user == null) {
            return false;
        }
        return user.canBreak(location);
    }

    private boolean getPlotSquared(Player player) {
        Location location = player.getLocation();
        String world = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        Plot plot = Plot.getPlot(com.plotsquared.core.location.Location.at(world, x, y, z));
        if (plot == null) { return false; }
        boolean owners = plot.getOwners().contains(player.getUniqueId());
        boolean trusted = plot.getTrusted().contains(player.getUniqueId());
        boolean member = plot.getMembers().contains(player.getUniqueId());
        return owners || trusted || member;
    }

    private boolean getWorldGuard(Player player) {
        int x = (int) player.getLocation().getX();
        int y = (int) player.getLocation().getY();
        int z = (int) player.getLocation().getZ();
        String region = plugin.getRegionConfig();
        World world = BukkitAdapter.adapt(player.getWorld());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(world);
        if (regions == null) {
            return false;
        }
        if (!regions.hasRegion(region)) {
            return false;
        }
        ProtectedRegion protectedRegion = regions.getRegion(region);
        if (protectedRegion == null) {
            return false;
        }
        return protectedRegion.contains(x, y, z);
    }

    private boolean getTowny(Player player) {
        Location location = player.getLocation();
        return PlayerCacheUtil.getCachePermission(player, location, Material.ARMOR_STAND, TownyPermission.ActionType.BUILD);
    }

    private boolean getLands(Player player) {
        UUID uuid = player.getUniqueId();
        Location location = player.getLocation();
        LandsIntegration api = LandsIntegration.of(plugin);
        Area area = api.getArea(location);
        if (area != null) {
            return area.isTrusted(uuid);
        }
        return false;
    }

    private boolean getBentoBox(Player player) {
        IslandsManager manager = BentoBox.getInstance().getIslandsManager();
        Optional<world.bentobox.bentobox.database.objects.Island> island = manager.getIslandAt(player.getLocation());
        return island.isPresent() && island.get().isAllowed(User.getInstance(player), Flags.PLACE_BLOCKS);
    }

    private boolean getSuperiorSkyblock(Player player) {
        Location location = player.getLocation();
        SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player);
        com.bgsoftware.superiorskyblock.api.island.Island island = SuperiorSkyblockAPI.getIslandAt(location);
        return island != null && (island.isMember(superiorPlayer) || island.isCoop(superiorPlayer));
    }

    private boolean getIridium(Player player) {
        Optional<Island> island = IridiumSkyblockAPI.getInstance().getIslandViaLocation(player.getLocation());
        com.iridium.iridiumskyblock.database.User user = IridiumSkyblockAPI.getInstance().getUser(player);
        return island.isPresent() && IridiumSkyblockAPI.getInstance().getIslandPermission(island.get(), user, PermissionType.BLOCK_BREAK);
    }
}
