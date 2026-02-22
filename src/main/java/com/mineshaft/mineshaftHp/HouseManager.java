package com.mineshaft.mineshaftHp;

import com.mineshaft.mineshaftapi.manager.player.json.JsonPlayerBridge;
import com.mineshaft.mineshaftapi.util.player.PlayerUtil;
import org.bukkit.Bukkit;

import java.util.Locale;
import java.util.UUID;

public class HouseManager {

    public static void setHouse(UUID uuid, House house) {
        if(!PlayerUtil.isValidPlayer(uuid)) return;
        if(house==null) return;
        JsonPlayerBridge.setCharacterDataValue(Bukkit.getPlayer(uuid),"hp_house",house.name().toLowerCase(Locale.ROOT));
    }

    public static House getHouse(UUID uuid) {
        if(!PlayerUtil.isValidPlayer(uuid)) return House.NONE;
        if(JsonPlayerBridge.getCharacterDataValue(Bukkit.getPlayer(uuid),"hp_house")==null) return House.NONE;
        return House.valueOf(JsonPlayerBridge.getCharacterDataValue(Bukkit.getPlayer(uuid),"hp_house").toUpperCase());
    }

    public static boolean isValidHouse(String houseName) {
        try {
            House.valueOf(houseName.toUpperCase());
            return true;
        } catch (Exception ignored) {}
        return false;
    }

    public static House parseHouse(String houseName) {
        if(!isValidHouse(houseName)) return House.NONE;
        return House.valueOf(houseName.toUpperCase());
    }
}
