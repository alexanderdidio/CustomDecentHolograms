package com.alexanderdidio.customdecentholograms.commands;

import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
import com.alexanderdidio.customdecentholograms.utils.Database;
import com.alexanderdidio.customdecentholograms.utils.Message;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HologramHide implements CommandExecutor {
    private final CustomDecentHolograms plugin;

    public HologramHide(CustomDecentHolograms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Database database = plugin.getDatabase();
        Message message = plugin.getMessage();
        boolean permission = sender.hasPermission("cdh.hide");
        boolean console = sender instanceof ConsoleCommandSender;
        Player player = Bukkit.getPlayer(sender.getName());
        UUID uuid;
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

        if (hologram.isVisibleState()) {
            hologram.setDefaultVisibleState(false);
            message.send(sender, "hologramHide");
        } else {
            hologram.setDefaultVisibleState(true);
            message.send(sender, "hologramShow");
        }

        return true;
    }
}
