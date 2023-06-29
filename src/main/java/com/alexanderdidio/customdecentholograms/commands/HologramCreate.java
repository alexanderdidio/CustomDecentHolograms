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
        boolean permission = sender.hasPermission("cdh.create");
        boolean console = sender instanceof ConsoleCommandSender;
        Player player = Bukkit.getPlayer(args[1]);
        UUID uuid;
        int hologramAmount;
        String hologramName;

        if (!(permission || console)) {
            message.send(sender, "noPermission");
            return true;
        }

        if (player == null) {
            message.send(sender, "invalidPlayer");
            return true;
        } else {
            uuid = player.getUniqueId();
            hologramAmount = database.countHolograms(uuid);
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

        message.send(sender, "hologramCreate", args[1]);
        message.send(player, "hologramReceive");
        return true;
    }
}