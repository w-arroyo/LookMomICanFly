package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AddressDTO {

    private int id;
    private String fullName, street, city, zipCode, country;

}
