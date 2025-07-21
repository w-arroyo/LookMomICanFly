package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostSummaryDTO {

    private String id, size;
    private Integer amount;
    private ProductSummaryDTO product;

}
