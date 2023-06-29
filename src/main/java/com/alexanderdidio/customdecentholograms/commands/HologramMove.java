package com.alexanderdidio.customdecentholograms.commands;

import com.alexanderdidio.customdecentholograms.API;
import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
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
    private final CustomDecentHolograms plugin;

    public HologramMove(CustomDecentHolograms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Database database = plugin.getDatabase();
        Message message = plugin.getMessage();
        API api = plugin.getAPI();
        boolean permission = sender.hasPermission("cdh.move");
        boolean console = sender instanceof ConsoleCommandSender;
        Player player = Bukkit.getPlayer(sender.getName());
        UUID uuid;
        Location location;
        int hologramAmount;
        String hologramName = args[1];

        if (!permission || console) {
            message.send(sender, "noPermission");
            return true;
        }

        if (player == null) {
            message.send(sender, "invalidSender");
            return true;
        } else {
            uuid = player.getUniqueId();
            location = player.getLocation();
            location.setY(location.getY()+2);
            hologramAmount = plugin.getDatabase().countHolograms(uuid);
        }

        if (hologramAmount == 0) {
            message.send(sender, "invalidList");
            return true;
        }

        if (!args[1].matches("\\d+")) {
            message.send(sender, "invalidHologram");
            return true;
        }

        int index = Integer.parseInt(hologramName)-1;

        if (!database.validateHologram(uuid, index)) {
            message.send(sender, "invalidHologram");
            return true;
        }

        Hologram hologram = database.getHologram(uuid, index);

        if (!api.validateAPI()) {
            message.send(sender, "invalidAPI");
            return true;
        }

        if (!api.checkLocation(player)) {
            message.send(sender, "invalidMove");
            return true;
        }

        DHAPI.moveHologram(hologram.getName(), location);
        message.send(sender, "hologramMove", args[1]);
        return true;
    }
}
