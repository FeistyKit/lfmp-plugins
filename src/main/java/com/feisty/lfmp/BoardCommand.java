package com.feisty.lfmp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class BoardCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        App.flushMaps();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage(ChatColor.YELLOW + "Hi!");
        }
        return false;
    }

}
