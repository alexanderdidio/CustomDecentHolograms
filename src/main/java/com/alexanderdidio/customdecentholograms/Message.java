package com.alexanderdidio.customdecentholograms;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Message {
    private final CustomDecentHolograms plugin;
    private static final File messageFile = new File("plugins/CustomDecentHolograms/messages.yml");
    private static YamlConfiguration messageConfig;

    public Message (CustomDecentHolograms plugin) {
        this.plugin = plugin;
    }

    public void send(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', format(message)));
    }

    public void send(CommandSender sender, String message, String variable) {
        String raw = format(message);
        String format = raw.replace("{0}", variable);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', format));
    }

    public void send(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', format(message)));
    }

    private String format(String message) {
        return messageConfig.getString("prefix") + messageConfig.getString(message);
    }

    public void loadMessages() {
        if (!messageFile.exists()) {
            plugin.saveResource("messages.yml", true);
        }
        messageConfig = YamlConfiguration.loadConfiguration(messageFile);
    }
}
