package com.alvarohdezarroyo.lookmomicanfly.Enums;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;

public enum FootballCategory {

    CLUB,
    NATIONAL;

    public static FootballCategory getFootballCategoryByName(String name){
        for(FootballCategory category: FootballCategory.values()){
            if(category.name().equalsIgnoreCase(name))
                return category;
        }
        throw new EntityNotFoundException("Football category does not exist.");
    }

}
