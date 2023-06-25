package com.alexanderdidio.customdecentholograms;

import com.alexanderdidio.customdecentholograms.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CustomDecentHolograms extends JavaPlugin {
    public static String api;
    public static int maxLines;
    public static int maxChars;
    public static Location spawnLocation;
    public static List<String> spawnLines = new ArrayList<>();

    @Override
    public void onEnable() {
        loadConfigs();
        PluginCommand command = getCommand("hg");
        command.setExecutor(new Command());
        command.setTabCompleter(new Command());
    }

    private void loadConfigs() {
        saveDefaultConfig();
        api = getConfig().getString("hologram.api");
        maxLines = getConfig().getInt("hologram.max-lines");
        maxChars = getConfig().getInt("hologram.max-characters");
        String world = getConfig().getString("hologram.spawn-location.world");
        double x = getConfig().getDouble("hologram.spawn-location.x");
        double y = getConfig().getDouble("hologram.spawn-location.y");
        double z = getConfig().getDouble("hologram.spawn-location.Z");
        spawnLines = getConfig().getStringList("hologram.spawn-lines");
        spawnLocation = new Location(Bukkit.getWorld(world), x, y, z);
        if (!Message.messageFile.exists()) {
            saveResource("messages.yml", false);
        }
        if (!Database.dataFile.exists()) {
            saveResource("data.yml", false);
        }
        Database.loadDatabase();
        Message.loadMessages();
    }
}
