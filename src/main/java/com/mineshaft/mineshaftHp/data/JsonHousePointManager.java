package com.mineshaft.mineshaftHp.data;

import com.mineshaft.mineshaftHp.House;
import com.mineshaft.mineshaftapi.MineshaftApi;
import com.mineshaft.mineshaftapi.util.save_data.GenericJsonManager;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;

public class JsonHousePointManager extends GenericJsonManager {

    // Overridden methods in the abstract parent class

    @Getter
    protected Comparator<House> housePointComparator = new Comparator<>() {
        @Override
        public int compare(House o1, House o2) {
            return getHousePoints(o1) - getHousePoints(o2);
        }
    };

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

    public void addHousePoints(House house, int points) {
        if(getHousePoints(house)+points>=0) {
            setHousePoints(house, getHousePoints(house) + points);
        }
    }

    // Loads data
    public int getHousePoints(House house) {
        return ((JsonHousePointClass) getData()).getHousePoints(house);
    }

    public HashMap<House, Integer> getHousePoints() {
        return ((JsonHousePointClass) getData()).getHousePoints();
    }
}
