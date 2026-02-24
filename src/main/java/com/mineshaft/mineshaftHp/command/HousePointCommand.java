package com.mineshaft.mineshaftHp.command;

import com.mineshaft.mineshaftHp.House;
import com.mineshaft.mineshaftHp.HouseManager;
import com.mineshaft.mineshaftHp.MineshaftHp;
import com.mineshaft.mineshaftapi.util.display.MessageUtil;
import com.mineshaft.mineshaftapi.util.player.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class HousePointCommand implements CommandExecutor {

    private static final List<String> generalFunctions = List.of("set","add","rem","remove","give","take");
    private static final List<String> playerFunctions = List.of("give_player","add_player","take_player","rem_player","remove_player");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String [] args) {
        // hp <set|add|remove> <house> <points>
        // hp get <house>
        // hp <give_player|take_player> <player> <points>

        if(args.length==1 && args[0].equalsIgnoreCase("list")) {
            ArrayList<House> houseOrder = new ArrayList<>(MineshaftHp.getHousePointManager().getHousePoints().keySet().stream().toList());
            houseOrder.remove(House.NONE);
            if(houseOrder.isEmpty()) {
                MessageUtil.sendErrorText(sender,"Error. Cannot get the house list.");
                return false;
            }
            houseOrder.sort(Comparator.comparingInt(o -> MineshaftHp.getHousePointManager().getHousePoints(o)));
            for(House house : houseOrder.reversed()) {
                if(house==House.NONE) continue;
                sender.sendMessage(house.getColour() + house.getDisplayName() + ": " + ChatColor.WHITE + MineshaftHp.getHousePointManager().getHousePoints(house));
            }
        } else if(args.length==2 && args[0].equalsIgnoreCase("get")) {
            if(HouseManager.isValidHouse(args[1])) {
                House house = HouseManager.parseHouse(args[1]);
                if(house==House.NONE) {
                    MessageUtil.sendErrorText(sender,"NONE is not a valid house.");
                    return false;
                }
                sendHousePointStatusMessage(sender,house,false);
            }
        } else if(args.length==3 && generalFunctions.contains(args[0])) {
            if(!HouseManager.isValidHouse(args[1]) || HouseManager.parseHouse(args[1])==House.NONE) {
                MessageUtil.sendErrorText(sender, "Please specify a valid house.");
                return false;
            }

            // Get the value
            int points;
            try {
                points = Integer.parseInt(args[2]);
            } catch (Exception ignored) {MessageUtil.sendErrorText(sender, "Please specify a valud number."); return false;}

            if(points<0) {
                MessageUtil.sendErrorText(sender,"The numeric value cannot be negative.");
                return false;
            }

            House house = HouseManager.parseHouse(args[1]);

            // Execute the command
            switch (args[0]) {
                case "set" -> {
                    MineshaftHp.getHousePointManager().setHousePoints(house,points);
                    sendHousePointStatusMessage(sender,house,true);
                }
                case "add","give" -> {
                    MineshaftHp.getHousePointManager().addHousePoints(house,points);
                    sendUpdateMessage(sender,house,points,"");
                }
                case "remove","rem","take" -> {
                    if(MineshaftHp.getHousePointManager().getHousePoints(house)-points<0) {
                        MessageUtil.sendErrorText(sender,"Cannot remove " + points + " points. " + house.getDisplayName() +" cannot have less than 0 points.");
                        return false;
                    }
                    MineshaftHp.getHousePointManager().addHousePoints(house,-points);
                    sendUpdateMessage(sender,house,-points,"");
                }
                default -> {}
            }
        } else if(args.length==3 && playerFunctions.contains(args[0])) {
            if(!PlayerUtil.isValidPlayer(args[1])) {
                MessageUtil.sendErrorText(sender, "Please specify a valid player name.");
                return false;
            }

            int points;
            try {
                points = Integer.parseInt(args[2]);
            } catch (Exception ignored) {MessageUtil.sendErrorText(sender, "Please specify a valid number."); return false;}

            if(points<0) {
                MessageUtil.sendErrorText(sender,"The numeric value cannot be negative.");
                return false;
            }

            UUID playerId = PlayerUtil.getPlayerId(args[1]);
            if(playerId == null ) {
                MessageUtil.sendErrorText(sender, "Error. The player specified does not exist.");
                return false;
            }
            if(HouseManager.getHouse(playerId)==null || HouseManager.getHouse(playerId)==House.NONE) {
                MessageUtil.sendErrorText(sender, "The specified player does not belong to any house.");
                return false;
            }

            House house = HouseManager.getHouse(playerId);

            switch (args[0]) {
                case "give_player","add_player" -> {
                    MineshaftHp.getHousePointManager().addHousePoints(house, points);
                    sendUpdateMessage(sender,house,points,args[1]);
                }
                case "take_player","rem_player","remove_player" -> {
                    if(MineshaftHp.getHousePointManager().getHousePoints(house)-points<0) {
                        MessageUtil.sendErrorText(sender,"Cannot remove " + points + " points. " + house.getDisplayName() +" cannot have less than 0 points.");
                        return false;
                    }
                    MineshaftHp.getHousePointManager().addHousePoints(house,-points);
                    sendUpdateMessage(sender,house,-points,args[1]);
                }
                default -> {}
            }

        } else {
            if(args.length==1&&args[0].equalsIgnoreCase("help")) {
                MessageUtil.sendErrorText(sender,"Syntax: ");
            } else {
                MessageUtil.sendErrorText(sender,"Invalid syntax. Use: ");
            }

            // Send syntax error.
            MessageUtil.sendErrorText(sender,"/hp <give|add|take|remove|set> <house> <amount>");
            MessageUtil.sendErrorText(sender,"/hp <give_player|add_player|take_player|remove_player> <player name> <amount>");
            MessageUtil.sendErrorText(sender,"/hp get");
            MessageUtil.sendErrorText(sender,"/hp help");
            MessageUtil.sendErrorText(sender,"You may use rem as an alias for remove");
        }

        return false;
    }

    public void sendHousePointStatusMessage(CommandSender sender, House house, boolean wasUpdated) {
        if(wasUpdated) {
            sender.sendMessage(house.getColour()+house.getDisplayName()+ ChatColor.WHITE +  " now has " + house.getColour() + MineshaftHp.getHousePointManager().getHousePoints(house) + ChatColor.WHITE + " points.");
        } else {
            sender.sendMessage(house.getColour()+house.getDisplayName()+ ChatColor.WHITE +  " has " + house.getColour() + MineshaftHp.getHousePointManager().getHousePoints(house) + ChatColor.WHITE + " points.");
        }
    }

    public void sendUpdateMessage(CommandSender sender, House house, int amount, String player) {
        if(player!=null&&!player.isEmpty()) {
            player = player + " from ";
        } else { player = ""; }
        if(amount>0) {
            if(amount == 1) {
                sender.sendMessage("Given " + house.getColour().toString() + amount + ChatColor.WHITE + " point to " + player + house.getColour()+house.getDisplayName());
            } else {
                sender.sendMessage("Given " + house.getColour().toString() + amount + ChatColor.WHITE + " points to " + player + house.getColour()+house.getDisplayName());
            }
        } else if(amount<0){
            if(amount == -1) {
                sender.sendMessage("Removed " + house.getColour().toString() + amount + ChatColor.WHITE + " point from " + player + house.getColour()+house.getDisplayName());
            } else {
                sender.sendMessage("Removed " + house.getColour().toString() + amount + ChatColor.WHITE + " points from " + player + house.getColour()+house.getDisplayName());
            }
        } else {
            MessageUtil.sendPlainText(sender,"No change in point number.");
        }
    }
}
