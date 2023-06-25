package com.alexanderdidio.customdecentholograms.commands;

import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
import com.alexanderdidio.customdecentholograms.Database;
import com.alexanderdidio.customdecentholograms.Message;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.DecentHologramsAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.UUID;

public class HologramCreate implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean permission = sender.hasPermission("cdh.create");
        boolean console = sender instanceof ConsoleCommandSender;
        Player player = Bukkit.getPlayer(args[1]);
        UUID uuid;
        int hologramAmount;
        String hologramName;

        if (!(permission || console)) {
            Message.send(sender, "noPermission");
            return true;
        }

        if (player == null) {
            Message.send(sender, "invalidPlayer");
            return true;
        } else {
            uuid = player.getUniqueId();
            hologramAmount = Database.countHolograms(uuid);
        }

        if (hologramAmount > 0) {
            hologramName = "uuid_" + uuid + "_" + (hologramAmount + 1);
        } else {
            hologramName = "uuid_" + uuid + "_" + 1;
        }

        Hologram hologram = DHAPI.createHologram(hologramName, CustomDecentHolograms.spawnLocation, true, CustomDecentHolograms.spawnLines);
        DecentHologramsAPI.get().getHologramManager().registerHologram(hologram);
        try {
            Database.createHologram(uuid, hologram);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Message.send(sender, "hologramCreate", args[1]);
        Message.send(player, "hologramReceive");
        return true;
    }
}