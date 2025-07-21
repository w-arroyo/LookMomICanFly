package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO extends TransactionDTO {

    private ShippingOptionDTO shippingOption;
    private double operationalFee;

}
