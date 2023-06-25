package com.alexanderdidio.customdecentholograms.commands;

import com.alexanderdidio.customdecentholograms.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HologramFormats implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("cdh.formats")) {
            Message.send(sender, "hologramFormats");
        } else {
            Message.send(sender, "noPermission");
        }
        return true;
    }
}
