package com.alexanderdidio.customdecentholograms.events;

import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.events.*;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class GriefPreventionEvents implements Listener {
    private final CustomDecentHolograms plugin;

    public GriefPreventionEvents(CustomDecentHolograms plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClaimDeleted(ClaimDeletedEvent event) {
        Claim claim = event.getClaim();
        UUID uuid = claim.getOwnerID();
        List<Hologram> holograms = plugin.getDatabase().listHolograms(uuid);
        if (holograms.size() > 0) {
            for (Hologram hologram : holograms) {
                Location location = hologram.getLocation();
                if (claim.contains(location, true, true)) {
                    DHAPI.moveHologram(hologram.getName(), plugin.getLocation());
                    plugin.getMessage().send(uuid, "hologramRelocated");
                }
            }
        }
    }

    @EventHandler
    public void onClaimExpiration(ClaimExpirationEvent event) {
        Claim claim = event.getClaim();
        UUID uuid = claim.getOwnerID();
        List<Hologram> holograms = plugin.getDatabase().listHolograms(uuid);
        if (holograms.size() > 0) {
            for (Hologram hologram : holograms) {
                Location location = hologram.getLocation();
                if (claim.contains(location, true, true)) {
                    DHAPI.moveHologram(hologram.getName(), plugin.getLocation());
                    plugin.getMessage().send(uuid, "hologramRelocated");
                }
            }
        }
    }

    @EventHandler
    public void onClaimResize(ClaimResizeEvent event) {
        Claim originalClaim = event.getFrom();
        Claim resizedClaim = event.getTo();
        UUID uuid = originalClaim.getOwnerID();
        List<Hologram> holograms = plugin.getDatabase().listHolograms(uuid);
        if (holograms.size() > 0) {
            for (Hologram hologram : holograms) {
                Location location = hologram.getLocation();
                if (originalClaim.contains(location, true, true)) {
                    if (!resizedClaim.contains(location, true, true)) {
                        DHAPI.moveHologram(hologram.getName(), plugin.getLocation());
                        plugin.getMessage().send(uuid, "hologramRelocated");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClaimTransfer(ClaimTransferEvent event) {
        Claim claim = event.getClaim();
        UUID oldOwner = claim.ownerID;
        List<Hologram> holograms = plugin.getDatabase().listHolograms(oldOwner);
        if (holograms.size() > 0) {
            for (Hologram hologram : holograms) {
                Location location = hologram.getLocation();
                if (claim.contains(location, true, true)) {
                    DHAPI.moveHologram(hologram.getName(), plugin.getLocation());
                    plugin.getMessage().send(oldOwner, "hologramRelocated");
                }
            }
        }
    }

    @EventHandler
    public void onTrustChanged(TrustChangedEvent event) {
        Collection<Claim> claims = event.getClaims();
        UUID uuid = UUID.fromString(event.getIdentifier());
        List<Hologram> holograms = plugin.getDatabase().listHolograms(uuid);
        if (holograms.size() > 0) {
            for (Hologram hologram : holograms) {
                Location location = hologram.getLocation();
                for (Claim claim : claims) {
                    if (claim.contains(location, true, true)) {
                        if (!claim.hasExplicitPermission(uuid, ClaimPermission.Build)) {
                            DHAPI.moveHologram(hologram.getName(), plugin.getLocation());
                            plugin.getMessage().send(uuid, "hologramRelocated");
                        }
                    }
                }
            }
        }
    }
}
