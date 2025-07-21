package com.alvarohdezarroyo.lookmomicanfly.Enums;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;

public enum UserType {
    ADMIN,
    STANDARD;

    public static UserType getUserType(String userType){
        for(UserType type: UserType.values()){
            if(type.name().equalsIgnoreCase(userType))
                return type;
        }
        throw new EntityNotFoundException("UserType does not exist.");
    }

}
