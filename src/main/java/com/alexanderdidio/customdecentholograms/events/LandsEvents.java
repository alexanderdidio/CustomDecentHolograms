package com.alexanderdidio.customdecentholograms.events;

import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.angeschossen.lands.api.events.LandDeleteEvent;
import me.angeschossen.lands.api.events.LandUntrustPlayerEvent;
import me.angeschossen.lands.api.events.land.claiming.selection.LandUnclaimSelectionEvent;
import me.angeschossen.lands.api.land.Area;
import me.angeschossen.lands.api.land.Land;
import me.ryanhamshire.GriefPrevention.Claim;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;

public class LandsEvents implements Listener {
    private final CustomDecentHolograms plugin;

    public LandsEvents(CustomDecentHolograms plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLandUnclaim(LandUnclaimSelectionEvent event) {
        Land land = event.getLand();
        UUID uuid = event.getPlayerUID();
        List<Hologram> holograms = plugin.getDatabase().listHolograms(uuid);
        if (holograms.size() > 0) {
            for (Hologram hologram : holograms) {
                Location location = hologram.getLocation();
                Area area = land.getArea(location);
                if (area != null) {
                    DHAPI.moveHologram(hologram.getName(), plugin.getLocation());
                    plugin.getMessage().send(uuid, "hologramRelocated");
                }
            }
        }
    }

    @EventHandler
    public void onLandDelete(LandDeleteEvent event) {
        Land land = event.getLand();
        UUID uuid = event.getPlayerUID();
        List<Hologram> holograms = plugin.getDatabase().listHolograms(uuid);
        if (holograms.size() > 0) {
            for (Hologram hologram : holograms) {
                Location location = hologram.getLocation();
                Area area = land.getArea(location);
                if (area != null) {
                    DHAPI.moveHologram(hologram.getName(), plugin.getLocation());
                    plugin.getMessage().send(uuid, "hologramRelocated");
                }
            }
        }
    }

    @EventHandler
    public void onLand(LandUntrustPlayerEvent event) {
        Land land = event.getLand();
        UUID uuid = event.getTargetUUID();
        List<Hologram> holograms = plugin.getDatabase().listHolograms(uuid);
        if (holograms.size() > 0) {
            for (Hologram hologram : holograms) {
                Location location = hologram.getLocation();
                Area area = land.getArea(location);
                if (area != null) {
                    DHAPI.moveHologram(hologram.getName(), plugin.getLocation());
                    plugin.getMessage().send(uuid, "hologramRelocated");
                }
            }
        }
    }
}
