package com.mineshaft.mineshaftHp.command;

import com.mineshaft.mineshaftHp.House;
import com.mineshaft.mineshaftHp.HouseManager;
import com.mineshaft.mineshaftapi.util.display.MessageUtil;
import com.mineshaft.mineshaftapi.util.player.PlayerUtil;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SetHouseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String [] args) {
        // Command:
        // /house set <player> <house>
        // /house get <player>

        if(args.length>=1) {
            switch (args[0]) {
                case "set" -> {
                    if(args.length!=3) {
                        MessageUtil.sendErrorText(sender, "Invalid number of arguments. Use:");
                        sendErrorMessage(sender);
                        return false;
                    }
                    if(!PlayerUtil.isValidPlayer(args[1])) {
                        sendWrongPlayerMessage(sender);
                        return false;
                    }
                    if(!HouseManager.isValidHouse(args[2])) {
                        MessageUtil.sendErrorText(sender, "Invalid house. Please select from:");
                        for (House house : House.values()) {
                            MessageUtil.sendColouredText(sender, house.getDisplayName(), house.getNamedTextColour());
                        }
                    }
                    HouseManager.setHouse(PlayerUtil.getPlayerId(args[1]),HouseManager.parseHouse(args[2]));
                    MessageUtil.sendColouredText(sender, "Set house successfully.", NamedTextColor.WHITE);
                    // Set the house
                }
                case "get" -> {
                    if(args.length!=2) {
                        MessageUtil.sendErrorText(sender, "Invalid number of arguments. Use:");
                        sendErrorMessage(sender);
                        return false;
                    }
                    if(!PlayerUtil.isValidPlayer(args[1])) {
                        sendWrongPlayerMessage(sender);
                        return false;
                    }
                    // Get the house and print it.
                    House house = HouseManager.getHouse(PlayerUtil.getPlayerId(args[1]));
                    sender.sendMessage("House: " + house.getColour() + house.getDisplayName());
                }
                default -> {
                    MessageUtil.sendErrorText(sender,"Invalid syntax. Use:");
                    sendErrorMessage(sender);
                }
            }
        }

        return false;
    }

    private void sendErrorMessage(CommandSender sender) {
        MessageUtil.sendErrorText(sender,"/house set <player> <house>");
        MessageUtil.sendErrorText(sender,"/house get <player>");
    }

    private void sendWrongPlayerMessage(CommandSender sender) {
        MessageUtil.sendErrorText(sender,"Invalid player.");
        MessageUtil.sendErrorText(sender,"Please specify a valid player, who is currently online.");
    }


}
