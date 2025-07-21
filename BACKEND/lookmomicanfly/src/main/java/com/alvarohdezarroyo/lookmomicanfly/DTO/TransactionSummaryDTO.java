package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummaryDTO {

    private String reference,size,id;
    private AddressDTO address;
    private ProductSummaryDTO product;
    private double amount;

}
