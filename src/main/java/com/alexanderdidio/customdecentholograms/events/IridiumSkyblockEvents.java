package com.alexanderdidio.customdecentholograms.events;

import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
import com.iridium.iridiumskyblock.api.IslandDeleteEvent;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;

public class IridiumSkyblockEvents implements Listener {
    private final CustomDecentHolograms plugin;

    public IridiumSkyblockEvents(CustomDecentHolograms plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onIslandDelete(IslandDeleteEvent event) {
        UUID uuid = event.getUser().getUuid();
        List<Hologram> holograms = plugin.getDatabase().listHolograms(uuid);
        if (holograms.size() > 0) {
            for (Hologram hologram : holograms) {
                Location location = hologram.getLocation();
                if (event.getIsland().isInIsland(location)) {
                    DHAPI.moveHologram(hologram.getName(), plugin.getLocation());
                    plugin.getMessage().send(uuid, "hologramRelocated");
                }
            }
        }
    }
}
