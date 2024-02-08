package com.alexanderdidio.customdecentholograms.events;

import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.angeschossen.lands.api.land.Area;
import me.angeschossen.lands.api.land.Land;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import world.bentobox.bentobox.api.events.island.IslandDeletedEvent;
import world.bentobox.bentobox.api.events.team.TeamKickEvent;
import world.bentobox.bentobox.api.events.team.TeamLeaveEvent;
import world.bentobox.bentobox.database.objects.IslandDeletion;

import java.util.List;
import java.util.UUID;

public class BentoBoxEvents implements Listener {
    private final CustomDecentHolograms plugin;

    public BentoBoxEvents(CustomDecentHolograms plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onIslandDelete(IslandDeletedEvent event) {
        UUID uuid = event.getPlayerUUID();
        List<Hologram> holograms = plugin.getDatabase().listHolograms(uuid);
        if (holograms.size() > 0) {
            for (Hologram hologram : holograms) {
                Location location = hologram.getLocation();
                if (event.getIsland().inIslandSpace(location)) {
                    DHAPI.moveHologram(hologram.getName(), plugin.getLocation());
                    plugin.getMessage().send(uuid, "hologramRelocated");
                }
            }
        }
    }

    @EventHandler
    public void onIslandLeave(TeamLeaveEvent event) {
        UUID uuid = event.getPlayerUUID();
        List<Hologram> holograms = plugin.getDatabase().listHolograms(uuid);
        if (holograms.size() > 0) {
            for (Hologram hologram : holograms) {
                Location location = hologram.getLocation();
                if (event.getIsland().inIslandSpace(location)) {
                    DHAPI.moveHologram(hologram.getName(), plugin.getLocation());
                    plugin.getMessage().send(uuid, "hologramRelocated");
                }
            }
        }
    }

    @EventHandler
    public void onIslandKick(TeamKickEvent event) {
        UUID uuid = event.getPlayerUUID();
        List<Hologram> holograms = plugin.getDatabase().listHolograms(uuid);
        if (holograms.size() > 0) {
            for (Hologram hologram : holograms) {
                Location location = hologram.getLocation();
                if (event.getIsland().inIslandSpace(location)) {
                    DHAPI.moveHologram(hologram.getName(), plugin.getLocation());
                    plugin.getMessage().send(uuid, "hologramRelocated");
                }
            }
        }
    }
}
