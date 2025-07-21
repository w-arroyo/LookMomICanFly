package com.alvarohdezarroyo.lookmomicanfly.Enums;

import java.util.Arrays;

public enum ProductCategory {

    SNEAKERS,
    CLOTHING,
    ACCESSORIES,
    COLLECTIBLES,
    SKATEBOARDS,
    ELECTRONICS,
    FOOTBALL,
    MUSIC;

    public static String[] getProductCategories(){
        return Arrays.stream(ProductCategory.values()).map(Enum::name).toArray(size -> new String[ProductCategory.values().length]);
    }

}
