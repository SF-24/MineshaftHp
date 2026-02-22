package com.mineshaft.mineshaftHp.data;

import com.mineshaft.mineshaftHp.House;
import com.mineshaft.mineshaftapi.MineshaftApi;
import com.mineshaft.mineshaftapi.manager.player.json.JsonSaveLoader;
import com.mineshaft.mineshaftapi.manager.player.json.SettingsDataClass;
import com.mineshaft.mineshaftapi.util.save_data.GenericJsonManager;
import com.mineshaft.mineshaftapi.util.save_data.GenericJsonSaveLoader;
import org.bukkit.entity.Player;

public class JsonHousePointManager extends GenericJsonManager {

    // Overridden methods in the abstract parent class

    @Override
    public Class<?> getSaveClass() {
        return JsonHousePointClass.class;
    }

    @Override
    public String getFileName() {
        return "house_points";
    }

    @Override
    public String getPath() {
        return MineshaftApi.getPluginDataPath();
    }

    // Edits and saves data
    public void setHousePoints(House house, int points) {
        JsonHousePointClass data = (JsonHousePointClass) getData();
        data.setHousePoints(house,points);
        saveFile(data);
    }

    // Loads data
    public int getHousePoints(House house) {
        return ((JsonHousePointClass) getData()).getHousePoints(house);
    }
}
