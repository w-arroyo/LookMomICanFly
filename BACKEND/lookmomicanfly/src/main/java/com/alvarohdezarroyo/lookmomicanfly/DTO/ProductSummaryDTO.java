package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummaryDTO {

    private String id, name, manufacturer, category;
    private Integer year;

}
