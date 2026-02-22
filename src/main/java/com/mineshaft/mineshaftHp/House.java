package com.mineshaft.mineshaftHp;

import lombok.Getter;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum House {

    NONE("No house", ChatColor.WHITE.toString(), NamedTextColor.WHITE),
    RAVENCLAW("Ravenclaw", ChatColor.BLUE.toString(), NamedTextColor.BLUE),
    GRYFFINDOR("Gryffindor", ChatColor.RED.toString(), NamedTextColor.RED),
    SLYTHERIN("Slytherin", ChatColor.GREEN.toString(), NamedTextColor.GREEN),
    HUFFLEPUFF("Hufflepuff", ChatColor.YELLOW.toString(), NamedTextColor.YELLOW);

    private final String displayName;
    private final String colour;
    private final NamedTextColor namedTextColour;

    House(String displayName, String colour, NamedTextColor namedTextColour) {
        this.displayName = displayName;
        this.colour=colour;
        this.namedTextColour=namedTextColour;
    }

    public static List<String> getIdList() {
        return Arrays.stream(House.values()).map(house -> house.name().toLowerCase()).collect(Collectors.toList());
    }
}
