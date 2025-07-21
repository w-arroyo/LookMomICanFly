package com.alvarohdezarroyo.lookmomicanfly.Enums;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;

public enum Material {
    Cotton,
    Wool, //lana
    Polyester,
    Nylon,
    Leather,
    Suede,
    Denim,
    Silk,
    Rubber,
    Plastic,
    Cashmere,
    Velvet;

    public static Material getMaterialFromName(String name){
        for(Material material: Material.values()){
            if(material.name().equalsIgnoreCase(name))
                return material;
        }
        throw new EntityNotFoundException("Material does not exist.");
    }

}