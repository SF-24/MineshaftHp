package com.mineshaft.mineshaftHp;

import com.mineshaft.mineshaftHp.command.HousePointCommand;
import com.mineshaft.mineshaftHp.command.HousePointTabCompleter;
import com.mineshaft.mineshaftHp.command.SetHouseCommand;
import com.mineshaft.mineshaftHp.command.SetHouseTabCompleter;
import com.mineshaft.mineshaftHp.data.JsonHousePointManager;
import com.mineshaft.mineshaftapi.util.Logger;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class MineshaftHp extends JavaPlugin {

    @Getter
    private static final JsonHousePointManager housePointManager = new JsonHousePointManager();

    @Override
    public void onEnable() {
        Logger.logInfo(
                "_  _ _ _  _ ____ ____ _  _ ____ ____ ___    _  _ ___ \n" +
                "|\\/| | |\\ | |___ [__  |__| |__| |___  |     |__| |__]\n" +
                "|  | | | \\| |___ ___] |  | |  | |     |     |  | |   "
        );
        // Plugin startup logic
        getCommand("house_points").setExecutor(new HousePointCommand());
        getCommand("house_points").setTabCompleter(new HousePointTabCompleter());
        getCommand("set_house").setExecutor(new SetHouseCommand());
        getCommand("set_house").setTabCompleter(new SetHouseTabCompleter());
        Logger.logInfo("Loaded plugin Mineshaft HP");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
