package com.mineshaft.mineshaftHp.command;

import com.mineshaft.mineshaftHp.House;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SetHouseTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String [] args) {
        if(args.length==1) {
            return StringUtil.copyPartialMatches(args[0],List.of("set","get","list"),new ArrayList<>());
        } else if(args.length==2&&(args[0].equalsIgnoreCase("set")||args[0].equalsIgnoreCase("get"))) {
            return StringUtil.copyPartialMatches(
                    args[1],
                    Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList())
                    ,new ArrayList<>()
            );
        } else if(args.length==3&&args[0].equalsIgnoreCase("set")) {
            return StringUtil.copyPartialMatches(args[2], House.getIdList(), new ArrayList<>());
        }
        return List.of();
    }
}
