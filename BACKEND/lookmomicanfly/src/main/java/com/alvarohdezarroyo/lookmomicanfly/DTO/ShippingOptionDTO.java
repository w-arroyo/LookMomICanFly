package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingOptionDTO {

    private String id,name,companyName, estimatedTime;
    private Double price;

}
