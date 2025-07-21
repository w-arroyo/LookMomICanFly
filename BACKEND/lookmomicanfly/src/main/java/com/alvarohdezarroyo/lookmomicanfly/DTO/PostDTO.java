package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    private String id, size;
    private AddressDTO addressDTO;
    private ProductSummaryDTO productSummaryDTO;
    private Integer amount;

}
