package com.alexanderdidio.customdecentholograms.utils;

import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class Message {
    private final CustomDecentHolograms plugin;
    private final File messageFile = new File("plugins/CustomDecentHolograms/messages.yml");
    private YamlConfiguration messageConfig;

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

    public void send(UUID uuid, String message) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return;
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', format(message)));
    }

    private String format(String message) {
        if (messageConfig.isList(message)) {
            List<String> lines = messageConfig.getStringList(message);
            StringBuilder stringBuilder = new StringBuilder();
            int i = 1;
            for (String line : lines) {
                if (i < lines.size()) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                } else {
                    stringBuilder.append(line);
                }
            }
            return stringBuilder.toString();
        } else {
            return messageConfig.getString("prefix") + messageConfig.getString(message);
        }
    }

    public void loadMessages() {
        if (!messageFile.exists()) {
            plugin.saveResource("messages.yml", true);
        }
        messageConfig = YamlConfiguration.loadConfiguration(messageFile);
    }
}
