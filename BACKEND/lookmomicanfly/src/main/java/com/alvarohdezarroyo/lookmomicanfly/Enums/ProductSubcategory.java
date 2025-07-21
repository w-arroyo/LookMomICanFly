package com.alvarohdezarroyo.lookmomicanfly.Enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum ProductSubcategory {

    HIGH(ProductCategory.SNEAKERS),
    LOW(ProductCategory.SNEAKERS),
    LIGHT(ProductCategory.SNEAKERS),

    PANTS(ProductCategory.CLOTHING),
    TEE(ProductCategory.CLOTHING),
    JACKET(ProductCategory.CLOTHING),
    HOODIE(ProductCategory.CLOTHING),
    CREWNECK(ProductCategory.CLOTHING),
    COAT(ProductCategory.CLOTHING),
    BOXERS(ProductCategory.CLOTHING),
    PUFFER(ProductCategory.CLOTHING),

    CAP(ProductCategory.ACCESSORIES),
    SHADES(ProductCategory.ACCESSORIES),
    GLOVES(ProductCategory.ACCESSORIES),
    BACKPACK(ProductCategory.ACCESSORIES),

    CARD(ProductCategory.COLLECTIBLES),
    FIGURE(ProductCategory.COLLECTIBLES),
    PLUSH(ProductCategory.COLLECTIBLES),

    SKATEBOARD(ProductCategory.SKATEBOARDS),
    SNOWBOARD(ProductCategory.SKATEBOARDS),

    GAMING(ProductCategory.ELECTRONICS),
    HARDWARE(ProductCategory.ELECTRONICS),
    MULTIMEDIA(ProductCategory.ELECTRONICS),

    JERSEYS(ProductCategory.FOOTBALL),
    TRAINING(ProductCategory.FOOTBALL),

    CD(ProductCategory.MUSIC),
    VINYL(ProductCategory.MUSIC),
    TAPE(ProductCategory.MUSIC);

    private final ProductCategory productCategory;

    ProductSubcategory(ProductCategory category){
        this.productCategory =category;
    }

    public static List<String> getSubcategoriesByCategory(String category){
        List<String> subcategories=new ArrayList<>();
        for (ProductSubcategory subcategory: ProductSubcategory.values()){
            if(subcategory.getProductCategory().name().equalsIgnoreCase(category))
                subcategories.add(subcategory.name());
        }
        return subcategories;
    }

    public static boolean checkIfSubcategoryBelongsToACategory(ProductCategory category,ProductSubcategory subcategory){
        return subcategory.getProductCategory().equals(category);
    }

}
