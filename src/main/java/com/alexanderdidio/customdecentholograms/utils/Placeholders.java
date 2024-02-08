package com.alexanderdidio.customdecentholograms.utils;

import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.nio.Buffer;
import java.util.UUID;

public class Placeholders extends PlaceholderExpansion {
    private final CustomDecentHolograms plugin;

    public Placeholders(CustomDecentHolograms plugin) {
        this.plugin = plugin;
        this.register();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "cdh";
    }

    @Override
    public @NotNull String getAuthor() {
        return "powerdev";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.3";
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {
        UUID uuid = offlinePlayer.getUniqueId();
        Player player = Bukkit.getPlayer(uuid);
        int owned = plugin.getDatabase().countHolograms(uuid);
        int permission = 0;
        if (player != null) {
            for (int i = 1; i <= 200; i++) {
                if (player.hasPermission("cdh.amount." + i)) {
                    permission = i;
                }
            }
        }
        switch (params) {
            case "owned":
                return String.valueOf(owned);
            case "permission":
                if (player != null) {
                    return String.valueOf(permission);
                }
            case "limit":
                if (owned > permission) {
                    return "False";
                } else {
                    return "True";
                }
            default:
                return null;
        }
    }
}
