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

public class HologramAdd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean permission = sender.hasPermission("cdh.add");
        boolean console = sender instanceof ConsoleCommandSender;
        Player player = Bukkit.getPlayer(sender.getName());
        UUID uuid;
        int hologramAmount;
        String hologramName = args[1];
        StringBuilder strings = new StringBuilder();

        for (int i = 2; i < args.length; i++) {
            strings.append(args[i]).append(" ");
        }

        String hologramText = String.valueOf(strings);

        if (!permission || console) {
            Message.send(sender, "noPermission");
            return true;
        }

        if (player == null) {
            Message.send(sender, "invalidSender");
            return true;
        } else {
            uuid = player.getUniqueId();
            hologramAmount = Database.countHolograms(uuid);
        }

        if (hologramAmount == 0) {
            Message.send(sender, "invalidList");
            return true;
        }

        if (!hologramName.matches("\\d+")) {
            Message.send(sender, "invalidHologram");
            return true;
        }

        if (hologramText.contains("%")) {
            Message.send(sender, "invalidChars");
            return true;
        }

        if (hologramText.length() > CustomDecentHolograms.maxChars) {
            Message.send(sender, "maximumChars", String.valueOf(CustomDecentHolograms.maxChars));
            return true;
        }

        int index = Integer.parseInt(hologramName)-1;

        if (!Database.validateHologram(uuid, index)) {
            Message.send(sender, "invalidHologram");
            return true;
        }

        Hologram hologram = Database.getHologram(uuid, index);
        int hologramLines = hologram.getPage(0).size();

        if (hologramLines >= CustomDecentHolograms.maxLines) {
            Message.send(sender, "maximumLines");
            return true;
        }

        DHAPI.addHologramLine(hologram, hologramText);
        Message.send(sender, "hologramAddLine", hologramName);
        return true;
    }
}
