package com.alexanderdidio.customdecentholograms;

import com.alexanderdidio.customdecentholograms.commands.Command;
import com.alexanderdidio.customdecentholograms.events.*;
import com.alexanderdidio.customdecentholograms.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CustomDecentHolograms extends JavaPlugin {
    private Database database;
    private Message message;
    private API api;
    private String apiConfig;
    private String regionConfig;
    private int maxLines;
    private int maxChars;
    private Location spawnLocation;
    private List<String> spawnLines = new ArrayList<>();

    @Override
    public void onEnable() {
        loadConfigs();
        loadAPIs();
        Command command = new Command(this);
        PluginCommand pluginCommand = getCommand("hg");
        pluginCommand.setExecutor(command);
        new Placeholders(this);
        new Metrics(this, 18931);
    }

    public void loadConfigs() {
        saveDefaultConfig();
        reloadConfig();
        apiConfig = getConfig().getString("hologram.api");
        regionConfig = getConfig().getString("hologram.region");
        maxLines = getConfig().getInt("hologram.max-lines");
        maxChars = getConfig().getInt("hologram.max-characters");
        String world = getConfig().getString("hologram.spawn-location.world");
        double x = getConfig().getDouble("hologram.spawn-location.x");
        double y = getConfig().getDouble("hologram.spawn-location.y");
        double z = getConfig().getDouble("hologram.spawn-location.Z");
        spawnLines = getConfig().getStringList("hologram.spawn-lines");
        spawnLocation = new Location(Bukkit.getWorld(world), x, y, z);
        database = new Database(this);
        message = new Message(this);
        api = new API(this);
        message.loadMessages();
        Bukkit.getScheduler().runTaskLater(this, () -> database.loadDatabase(),20);
    }

    private void loadAPIs() {
        if (getServer().getPluginManager().isPluginEnabled("PlotSquared")) {
            new PlotSquaredEvents(this);
        }
        if (getServer().getPluginManager().isPluginEnabled("GriefDefender")) {
            new GriefDefenderEvents(this);
        }
        if (getServer().getPluginManager().isPluginEnabled("Lands")) {
            Bukkit.getPluginManager().registerEvents(new LandsEvents(this), this);
        }
        if (getServer().getPluginManager().isPluginEnabled("BentoBox")) {
            Bukkit.getPluginManager().registerEvents(new BentoBoxEvents(this), this);
        }
        if (getServer().getPluginManager().isPluginEnabled("GriefPrevention")) {
            Bukkit.getPluginManager().registerEvents(new GriefPreventionEvents(this), this);
        }
        if (getServer().getPluginManager().isPluginEnabled("SuperiorSkyblock2")) {
            Bukkit.getPluginManager().registerEvents(new SuperiorSkyblockEvents(this), this);
        }
        if (getServer().getPluginManager().isPluginEnabled("IridiumSkyblock")) {
            Bukkit.getPluginManager().registerEvents(new IridiumSkyblockEvents(this), this);
        }
    }

    public Database getDatabase() {
        return database;
    }

    public Message getMessage() {
        return message;
    }

    public API getAPI() {
        return api;
    }

    public String getAPIConfig() {
        return apiConfig;
    }

    public String getRegionConfig() {
        return regionConfig;
    }

    public int getMaxLines() {
        return maxLines;
    }

    public int getMaxChars() {
        return maxChars;
    }

    public List<String> getLines() {
        return spawnLines;
    }

    public Location getLocation() {
        return spawnLocation;
    }
}
