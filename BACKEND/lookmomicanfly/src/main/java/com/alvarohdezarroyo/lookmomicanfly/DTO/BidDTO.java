package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidDTO extends PostDTO {

    private ShippingOptionDTO shippingOptionDTO;
    private Double operatingFee;

}
