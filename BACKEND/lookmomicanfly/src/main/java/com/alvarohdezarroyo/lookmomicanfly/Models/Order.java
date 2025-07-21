package com.alvarohdezarroyo.lookmomicanfly.Models;

import com.alvarohdezarroyo.lookmomicanfly.Enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "reference", unique = true)
    @NotNull
    @NotBlank
    private String reference;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bid_id")
    @NotNull
    private Bid bid;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tracking_and_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "tracking_number_id")
    )
    private List<TrackingNumber> trackingNumbers;

}
