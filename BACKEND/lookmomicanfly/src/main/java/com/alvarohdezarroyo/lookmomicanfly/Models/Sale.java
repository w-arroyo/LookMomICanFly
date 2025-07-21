package com.alvarohdezarroyo.lookmomicanfly.Models;

import com.alvarohdezarroyo.lookmomicanfly.Enums.SaleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "reference", unique = true, nullable = false, updatable = false)
    private String reference;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private SaleStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ask_id")
    @NotNull
    private Ask ask;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tracking_and_sales",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "tracking_number_id")
    )
    private List<TrackingNumber> trackingNumbers;

}
