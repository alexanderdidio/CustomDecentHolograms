package com.alexanderdidio.customdecentholograms;

import com.alexanderdidio.customdecentholograms.commands.Command;
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
        database = new Database(this);
        message = new Message(this);
        api = new API(this);
        saveDefaultConfig();
        database.loadDatabase();
        message.loadMessages();
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
        Command command = new Command(this);
        PluginCommand pluginCommand = getCommand("hg");
        pluginCommand.setExecutor(command);
        new Metrics(this, 18931);
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
