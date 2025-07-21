package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "bids")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bid extends Post{

    @ManyToOne
    @JoinColumn(name = "shipping_option_id")
    @NotNull
    private ShippingOption shippingOption;

    @Column(name = "operational_fee")
    @NotNull
    private Double operationalFee;

    @OneToOne(fetch = FetchType.EAGER)
    @NotNull
    private Payment payment;

}
