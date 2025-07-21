package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "sale_id")
    @NotNull
    private Sale sale;

    @OneToOne
    @JoinColumn(name = "order_id")
    @NotNull
    private Order order;

    @Column (name = "date", updatable = false)
    @NotNull
    private LocalDateTime date= LocalDateTime.now();

}
