package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionOverviewDTO {

    private String id,size, status;
    private Integer amount;
    private ProductSummaryDTO product;
    private String date;

}
