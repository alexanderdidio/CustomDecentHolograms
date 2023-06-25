package com.alexanderdidio.customdecentholograms.commands;

import com.alexanderdidio.customdecentholograms.Database;
import com.alexanderdidio.customdecentholograms.Message;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class HologramList implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean permission = sender.hasPermission("cdh.list");
        boolean console = sender instanceof ConsoleCommandSender;
        Player player = Bukkit.getPlayer(sender.getName());
        UUID uuid;
        int hologramAmount;

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

        StringBuilder strings = new StringBuilder();
        List<Hologram> holograms = Database.listHolograms(uuid);

        for (int i = 0; i < holograms.size(); i++) {
            Hologram hologram = holograms.get(i);
            String name = String.valueOf(i + 1);
            String world = hologram.getLocation().getWorld().getName();
            int x = hologram.getLocation().getBlockX();
            int y = hologram.getLocation().getBlockY();
            int z = hologram.getLocation().getBlockZ();
            String message = String.format("&f%s &7(%s %d %d %d) ", name, world, x, y, z);
            strings.append(message);
        }

        Message.send(sender, "hologramList", String.valueOf(strings));
        return true;
    }
}
