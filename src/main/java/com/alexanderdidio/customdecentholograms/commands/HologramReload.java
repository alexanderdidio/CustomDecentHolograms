package com.alexanderdidio.customdecentholograms.commands;

import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
import com.alexanderdidio.customdecentholograms.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class HologramReload implements CommandExecutor {
    private final CustomDecentHolograms plugin;

    public HologramReload(CustomDecentHolograms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Message message = plugin.getMessage();
        boolean permission = sender.hasPermission("cdh.reload");
        boolean console = sender instanceof ConsoleCommandSender;
        if (!permission || console) {
            message.send(sender, "noPermission");
            return true;
        }
        plugin.loadConfigs();
        message.send(sender, "hologramReload");
        return true;
    }
}
