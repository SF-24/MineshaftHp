package com.mineshaft.mineshaftHp.data;

import com.mineshaft.mineshaftHp.House;
import com.mineshaft.mineshaftapi.manager.player.json.JsonSaveObject;
import lombok.Getter;

import java.util.HashMap;

public class JsonHousePointClass extends JsonSaveObject {

    @Getter
    protected HashMap<House, Integer> housePoints = new HashMap<>();

    public JsonHousePointClass() {
        for(House house : House.values()) {
            if(house!=House.NONE&&!housePoints.keySet().contains(house)) {
                housePoints.put(house,0);
            }
        }
    }

    public void setHousePoints(House house, int points) {
        if(house!=House.NONE) housePoints.put(house,points);
    }

    public int getHousePoints(House house) {
        if(house==House.NONE||!housePoints.containsKey(house)) return 0;
        return housePoints.get(house);
    }

}
