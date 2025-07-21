package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AddressDTO {

    private String id;
    private String userId;
    private String fullName, street, city, zipCode, country;

}
