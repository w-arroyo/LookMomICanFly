package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "asks")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ask extends Post {

    @ManyToOne
    @JoinColumn(name = "fee_id")
    @NotNull
    private SellingFee sellingFee;

    @Column(name = "shipping_fee")
    @NotNull
    private Double shippingFee;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_account_id")
    @NotNull
    private BankAccount bankAccount;


}
