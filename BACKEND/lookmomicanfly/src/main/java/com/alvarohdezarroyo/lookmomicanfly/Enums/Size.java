package com.alvarohdezarroyo.lookmomicanfly.Enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Getter
public enum Size {

    NO_SIZE("ONE-SIZE", Set.of(ProductCategory.ACCESSORIES, ProductCategory.COLLECTIBLES, ProductCategory.SKATEBOARDS, ProductCategory.MUSIC, ProductCategory.ELECTRONICS)),
    SMALL("S", Set.of(ProductCategory.CLOTHING, ProductCategory.FOOTBALL)),
    MEDIUM("M", Set.of(ProductCategory.CLOTHING, ProductCategory.FOOTBALL)),
    LARGE("L", Set.of(ProductCategory.CLOTHING, ProductCategory.FOOTBALL)),
    EXTRA_LARGE("XL", Set.of(ProductCategory.CLOTHING, ProductCategory.FOOTBALL)),
    THIRTY_EIGHT("38", Set.of(ProductCategory.SNEAKERS)),
    THIRTY_NINE("39", Set.of(ProductCategory.SNEAKERS)),
    FORTY("40", Set.of(ProductCategory.SNEAKERS)),
    FORTY_AND_HALF("40.5", Set.of(ProductCategory.SNEAKERS)),
    FORTY_ONE("41", Set.of(ProductCategory.SNEAKERS)),
    FORTY_TWO("42", Set.of(ProductCategory.SNEAKERS)),
    FORTY_TWO_AND_HALF("42.5", Set.of(ProductCategory.SNEAKERS)),
    FORTY_THREE("43", Set.of(ProductCategory.SNEAKERS)),
    FORTY_FOUR("44", Set.of(ProductCategory.SNEAKERS)),
    FORTY_FOUR_AND_HALF("44.5", Set.of(ProductCategory.SNEAKERS)),
    FORTY_FIVE("45", Set.of(ProductCategory.SNEAKERS));

    private final String value;
    private final Set<ProductCategory> categories;

    Size(String value, Set<ProductCategory> categories) {
        this.value = value;
        this.categories=categories;
    }

    public static List<Size> getSizesByCategory(ProductCategory category){
        return Arrays.stream(Size.values()).filter(size -> size.getCategories().contains(category)).toList();
    }

}
