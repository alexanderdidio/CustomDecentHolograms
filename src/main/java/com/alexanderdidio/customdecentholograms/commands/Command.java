package com.alexanderdidio.customdecentholograms.commands;

import com.alexanderdidio.customdecentholograms.Database;
import com.alexanderdidio.customdecentholograms.Message;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.*;

public class Command implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length == 0) {
            Message.send(sender, "usageArgs");
            return true;
        }

        if (args[0].equalsIgnoreCase("create")) {
            if (args.length == 2) {
                HologramCreate hologramCreateCommand = new HologramCreate();
                return hologramCreateCommand.onCommand(sender, command, label, args);
            } else {
                Message.send(sender, "usageCreate");
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("move")) {
            if (args.length == 2) {
                HologramMove hologramMoveCommand = new HologramMove();
                return hologramMoveCommand.onCommand(sender, command, label, args);
            } else {
                Message.send(sender, "usageMove");
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("edit")) {
            if (args.length > 3) {
                HologramEdit hologramEditCommand = new HologramEdit();
                return hologramEditCommand.onCommand(sender, command, label, args);
            } else {
                Message.send(sender, "usageEdit");
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("add")) {
            if (args.length > 2) {
                HologramAdd hologramAddCommand = new HologramAdd();
                return hologramAddCommand.onCommand(sender, command, label, args);
            } else {
                Message.send(sender, "usageAdd");
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("remove")) {
            if (args.length == 3) {
                HologramRemove hologramRemoveCommand = new HologramRemove();
                return hologramRemoveCommand.onCommand(sender, command, label, args);
            } else {
                Message.send(sender, "usageRemove");
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("list")) {
            if (args.length == 1) {
                HologramList hologramListCommand = new HologramList();
                return hologramListCommand.onCommand(sender, command, label, args);
            } else {
                Message.send(sender, "usageList");
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("formats")) {
            if (args.length == 1) {
                HologramFormats hologramFormatsCommand = new HologramFormats();
                return hologramFormatsCommand.onCommand(sender, command, label, args);
            } else {
                Message.send(sender, "usageFormats");
                return true;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        List<String> tab = new ArrayList<>();
        String cmd = args[0].toLowerCase();
        Player player;
        UUID uuid;

        if (args.length == 1) {
            tab.add("create");
            tab.add("move");
            tab.add("edit");
            tab.add("add");
            tab.add("remove");
            tab.add("list");
            tab.add("formats");
        }

        if (args.length == 2 && cmd.equals("create")) {
            String entry = args[1].toLowerCase();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                String name = onlinePlayer.getName();
                if (name.toLowerCase().startsWith(entry)) {
                    tab.add(name);
                }
            }
            return tab;
        }

        if (sender instanceof ConsoleCommandSender) {
            return tab;
        }

        if (args.length == 2 && Arrays.asList("move", "edit", "add", "remove").contains(cmd)) {
            player = (Player) sender;
            uuid = player.getUniqueId();
            for (int i = Database.countHolograms(uuid); i > 0; i--) {
                tab.add(String.valueOf(i));
            }
        }

        if (args.length == 3 && Arrays.asList("edit", "remove").contains(cmd)) {
            player = (Player) sender;
            uuid = player.getUniqueId();
            if (args[1].matches("\\d+")) {
                int index = Integer.parseInt(args[1])-1;
                if (Database.validateHologram(uuid, index)) {
                    Hologram hologram = Database.getHologram(uuid, index);
                    for (int lines = hologram.getPage(0).size(); lines > 0; lines--) {
                        tab.add(String.valueOf(lines));
                    }
                }
            }
        }
        return tab;
    }
}
