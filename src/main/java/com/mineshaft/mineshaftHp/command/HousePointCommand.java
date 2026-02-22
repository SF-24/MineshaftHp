package com.mineshaft.mineshaftHp.command;

import com.mineshaft.mineshaftHp.House;
import com.mineshaft.mineshaftHp.HouseManager;
import com.mineshaft.mineshaftHp.MineshaftHp;
import com.mineshaft.mineshaftapi.util.display.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class HousePointCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String [] args) {
        // hp <set|add|remove> <house> <points>
        // hp get <house>
        // hp <give_player|take_player> <player> <points>

        if(args[0].equalsIgnoreCase("get") && args.length==2) {
            if(HouseManager.isValidHouse(args[1])) {
                House house = HouseManager.parseHouse(args[1]);
                if(house==House.NONE) {
                    MessageUtil.sendErrorText(sender,"NONE is not a valid house.");
                    return false;
                }
                sender.sendMessage(house.getColour()+house.getDisplayName()+ ChatColor.WHITE + " has " + house.getColour() + MineshaftHp.getHousePointManager().getHousePoints(house) + ChatColor.WHITE + " points.");
            }
        } else if(args.length==3) {
            // TODO:
        } else {
            // Send syntax error.
        }

        return false;
    }
}
