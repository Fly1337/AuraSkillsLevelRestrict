package com.fly.auraskillslevelrestrict;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission("auraskillslevelrestrict.reload")) {
            Addon.data.clear();
            Addon.load();
            commandSender.sendMessage("Reloaded!");
            return true;
        }
        return false;
    }
}
