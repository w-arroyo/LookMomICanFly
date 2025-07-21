package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "payment_intent_id", unique = true)
    @NotNull
    @NotBlank
    private String paymentIntentId;

    @Column(name = "amount")
    @NotNull
    private Double amount;

    @Column(name = "currency")
    @NotNull
    @NotBlank
    private String currency;

    @Column(name = "status")
    @NotNull
    @NotBlank
    private String status;

    @Column(name = "date")
    @NotNull
    private LocalDateTime date=LocalDateTime.now();

}
