package com.alexanderdidio.customdecentholograms.commands;

import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
import com.alexanderdidio.customdecentholograms.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HologramFormats implements CommandExecutor {
    private final CustomDecentHolograms plugin;

    public HologramFormats(CustomDecentHolograms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Message message = plugin.getMessage();
        if (sender.hasPermission("cdh.formats")) {
            message.send(sender, "hologramFormats");
        } else {
            message.send(sender, "noPermission");
        }
        return true;
    }
}
