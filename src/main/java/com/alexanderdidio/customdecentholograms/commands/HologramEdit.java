package com.alexanderdidio.customdecentholograms.commands;

import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
import com.alexanderdidio.customdecentholograms.Database;
import com.alexanderdidio.customdecentholograms.Message;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HologramEdit implements CommandExecutor {
    private final CustomDecentHolograms plugin;

    public HologramEdit(CustomDecentHolograms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Database database = plugin.getDatabase();
        Message message = plugin.getMessage();
        boolean permission = sender.hasPermission("cdh.edit");
        boolean console = sender instanceof ConsoleCommandSender;
        Player player = Bukkit.getPlayer(sender.getName());
        UUID uuid;
        int hologramAmount;
        String hologramName = args[1];
        String hologramLine = args[2];
        StringBuilder strings = new StringBuilder();

        for (int i = 3; i < args.length; i++) {
            strings.append(args[i]).append(" ");
        }

        String hologramText = String.valueOf(strings);

        if (!permission || console) {
            message.send(sender, "noPermission");
            return true;
        }

        if (player == null) {
            message.send(sender, "invalidSender");
            return true;
        } else {
            uuid = player.getUniqueId();
            hologramAmount = database.countHolograms(uuid);
        }

        if (hologramAmount == 0) {
            message.send(sender, "invalidList");
            return true;
        }

        if (!hologramName.matches("\\d+")) {
            message.send(sender, "invalidHologram");
            return true;
        }

        if (!hologramLine.matches("\\d+")) {
            message.send(sender, "invalidLine");
            return true;
        }

        if (hologramText.contains("%")) {
            message.send(sender, "invalidChars");
            return true;
        }

        if (hologramText.length() > plugin.getMaxChars()) {
            message.send(sender, "maximumChars", String.valueOf(plugin.getMaxChars()));
            return true;
        }

        int index = Integer.parseInt(hologramName)-1;
        int line = Integer.parseInt(hologramLine)-1;

        if (!database.validateHologram(uuid, index)) {
            message.send(sender, "invalidHologram");
            return true;
        }

        Hologram hologram = database.getHologram(uuid, index);

        if (line < 0 || line > hologram.getPage(0).size()-1) {
            message.send(sender, "invalidLine");
            return true;
        }

        DHAPI.setHologramLine(hologram, line, hologramText);
        message.send(sender, "hologramEditLine", hologramName);
        return true;
    }
}
