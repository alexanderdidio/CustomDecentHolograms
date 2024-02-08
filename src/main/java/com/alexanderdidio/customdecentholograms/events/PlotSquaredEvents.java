package com.alexanderdidio.customdecentholograms.events;

import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.events.PlotChangeOwnerEvent;
import com.plotsquared.core.events.PlotDeleteEvent;
import com.plotsquared.core.plot.Plot;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.util.eventbus.Subscribe;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class PlotSquaredEvents {
    private final CustomDecentHolograms plugin;

    public PlotSquaredEvents(CustomDecentHolograms plugin) {
        this.plugin = plugin;
        eventSubscribe();
    }

    public void eventSubscribe() {
        PlotAPI api = new PlotAPI();
        api.registerListener(this);
    }

    @Subscribe
    public void onPlotDelete(PlotDeleteEvent event) {
        Plot plot = event.getPlot();
        HashSet<UUID> members = plot.getMembers();
        for (UUID member : members) {
            List<Hologram> holograms = plugin.getDatabase().listHolograms(member);
            if (holograms.size() > 0) {
                for (Hologram hologram : holograms) {
                    Location location = hologram.getLocation();
                    int x = location.getBlockX();
                    int y = location.getBlockY();
                    int z = location.getBlockZ();
                    BlockVector3 blockVector3 = BlockVector3.at(x, y, z);
                    if (plot.getLargestRegion().contains(blockVector3)) {
                        DHAPI.moveHologram(hologram.getName(), plugin.getLocation());
                        plugin.getMessage().send(member, "hologramRelocated");
                    }
                }
            }
        }
    }

    @Subscribe
    public void onOwnerChange(PlotChangeOwnerEvent event) {
        Plot plot = event.getPlot();
        UUID uuid = event.getOldOwner();
        List<Hologram> holograms = plugin.getDatabase().listHolograms(uuid);
        if (holograms.size() > 0) {
            for (Hologram hologram : holograms) {
                Location location = hologram.getLocation();
                int x = location.getBlockX();
                int y = location.getBlockY();
                int z = location.getBlockZ();
                BlockVector3 blockVector3 = BlockVector3.at(x, y, z);
                if (plot.getLargestRegion().contains(blockVector3)) {
                    DHAPI.moveHologram(hologram.getName(), plugin.getLocation());
                    plugin.getMessage().send(uuid, "hologramRelocated");
                }
            }
        }
    }
}
