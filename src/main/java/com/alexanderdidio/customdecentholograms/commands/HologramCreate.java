package com.alexanderdidio.customdecentholograms.commands;

import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
import com.alexanderdidio.customdecentholograms.utils.API;
import com.alexanderdidio.customdecentholograms.utils.Database;
import com.alexanderdidio.customdecentholograms.utils.Message;
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
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class HologramCreate implements CommandExecutor {
    private final CustomDecentHolograms plugin;

    public HologramCreate(CustomDecentHolograms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Database database = plugin.getDatabase();
        Message message = plugin.getMessage();
        List<String> lines = plugin.getLines();
        API api = plugin.getAPI();
        boolean permission = sender.hasPermission("cdh.create");
        boolean console = sender instanceof ConsoleCommandSender;
        Player player;
        UUID uuid;
        int hologramAmount;
        String hologramName;

        if (!(permission || console)) {
            message.send(sender, "noPermission");
            return true;
        }

        // Check if creating hologram for self or others

        if (args.length == 1) {
            player = (Player) sender;
        } else {
            if (!console && !sender.isOp()) {
                message.send(sender, "noPermissionCreateOthers");
                return true;
            } else {
                player = Bukkit.getPlayer(args[1]);
            }
        }

        if (player == null) {
            message.send(sender, "invalidPlayer");
            return true;
        } else {
            uuid = player.getUniqueId();
            hologramAmount = database.countHolograms(uuid);
        }

        if (!api.validateAPI()) {
            message.send(sender, "invalidAPI");
            return true;
        }

        if (!api.checkIsOwner(player)) {
            message.send(sender, "noPermissionCreateHere");
            return true;
        }

        // Check permission for allowed number of holograms
        int permissionAmount = 0;

        for (int i = 1; i <= 200; i++) {
            if (player.hasPermission("cdh.amount." + i)) {
                permissionAmount = i;
            }
        }

        if (hologramAmount >= permissionAmount) {
            if (args.length == 1) {
                message.send(sender, "playerMaximumHolograms");
            } else {
                message.send(sender, "senderMaximumHolograms");
            }
            return true;
        }

        if (hologramAmount > 0) {
            hologramName = "uuid_" + uuid + "_" + (hologramAmount + 1);
        } else {
            hologramName = "uuid_" + uuid + "_" + 1;
        }

        lines.replaceAll(s -> s.replaceAll(Pattern.quote("{0}"), player.getName()));
        Hologram hologram = DHAPI.createHologram(hologramName, plugin.getLocation(), true, lines);
        DecentHologramsAPI.get().getHologramManager().registerHologram(hologram);

        try {
            database.createHologram(uuid, hologram);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (args.length != 1) {
            message.send(player, "hologramCreateSender", args[1]);
        }
        message.send(player, "hologramCreatePlayer");
        return true;
    }
}