package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleDTO extends TransactionDTO {

    private double shippingFee;
    private int percentage;

}
