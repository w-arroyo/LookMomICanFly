package com.alvarohdezarroyo.lookmomicanfly.Enums;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;

public enum Season {
    SS,
    FW;

    public static Season getSeasonFromName(String name){
        for(Season season: Season.values()){
            if(season.name().equalsIgnoreCase(name))
                return season;
        }
        throw new EntityNotFoundException("Season does not exist.");
    }

}
