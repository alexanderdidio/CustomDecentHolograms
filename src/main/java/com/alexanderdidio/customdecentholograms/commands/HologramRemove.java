package com.alexanderdidio.customdecentholograms.commands;

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

public class HologramRemove implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean permission = sender.hasPermission("cdh.remove");
        boolean console = sender instanceof ConsoleCommandSender;
        Player player = Bukkit.getPlayer(sender.getName());
        UUID uuid;
        int hologramAmount;
        String hologramName = args[1];
        String hologramLine = args[2];

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

        if (!hologramLine.matches("\\d+")) {
            Message.send(sender, "invalidLine");
            return true;
        }

        int index = Integer.parseInt(hologramName)-1;
        int line = Integer.parseInt(hologramLine)-1;

        if (!Database.validateHologram(uuid, index)) {
            Message.send(sender, "invalidHologram");
            return true;
        }

        Hologram hologram = Database.getHologram(uuid, index);

        if (line < 0 || line > hologram.getPage(0).size()-1) {
            Message.send(sender, "invalidLine");
            return true;
        }

        DHAPI.removeHologramLine(hologram, line);
        Message.send(sender, "hologramRemoveLine", hologramName);
        return true;
    }
}
