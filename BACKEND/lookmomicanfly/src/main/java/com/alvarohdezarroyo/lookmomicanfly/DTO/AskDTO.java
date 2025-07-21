package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskDTO extends PostDTO{

    private Double shippingFee;
    private SellingFeeDTO sellingFee;

}
