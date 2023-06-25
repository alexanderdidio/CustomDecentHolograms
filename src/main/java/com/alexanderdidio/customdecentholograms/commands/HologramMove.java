package com.alexanderdidio.customdecentholograms.commands;

import com.alexanderdidio.customdecentholograms.API;
import com.alexanderdidio.customdecentholograms.Database;
import com.alexanderdidio.customdecentholograms.Message;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HologramMove implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean permission = sender.hasPermission("cdh.move");
        boolean console = sender instanceof ConsoleCommandSender;
        Player player = Bukkit.getPlayer(sender.getName());
        UUID uuid;
        Location location;
        int hologramAmount;
        String hologramName = args[1];

        if (!permission || console) {
            Message.send(sender, "noPermission");
            return true;
        }

        if (player == null) {
            Message.send(sender, "invalidSender");
            return true;
        } else {
            uuid = player.getUniqueId();
            location = player.getLocation();
            location.setY(location.getY()+2);
            hologramAmount = Database.countHolograms(uuid);
        }

        if (hologramAmount == 0) {
            Message.send(sender, "noHolograms");
            return true;
        }

        if (!args[1].matches("\\d+")) {
            Message.send(sender, "invalidHologram");
            return true;
        }

        int index = Integer.parseInt(hologramName)-1;
        Hologram hologram = Database.getHologram(uuid, index);

        if (!Database.validateHologram(uuid, index)) {
            Message.send(sender, "invalidHologram");
            return true;
        }

        if (API.validateAPI()) {
            Message.send(sender, "invalidAPI");
            return true;
        }

        if (!API.checkLocation(player)) {
            Message.send(sender, "invalidMove");
            return true;
        }

        DHAPI.moveHologram(hologram.getName(), location);
        Message.send(sender, "hologramMove", args[1]);
        return true;
    }
}
