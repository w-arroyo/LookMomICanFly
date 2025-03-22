package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private String size,trackingNumber;
    private AddressDTO address;
    private ProductSummaryDTO product;
    private double amount;
    private TransactionStatusDTO status;

}
