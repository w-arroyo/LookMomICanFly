package com.alvarohdezarroyo.lookmomicanfly.Enums;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;

public enum Format {
    Single,
    Album,
    Mixtape,
    ExtendedPlay,
    LongPlay;

    public static Format getFormatByName(String name){
        for (Format format: Format.values()){
            if(format.name().equalsIgnoreCase(name))
                return format;
        }
        throw new EntityNotFoundException("Format does not ");
    }

}
