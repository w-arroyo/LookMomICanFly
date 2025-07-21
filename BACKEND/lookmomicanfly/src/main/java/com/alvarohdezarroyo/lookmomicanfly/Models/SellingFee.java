package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "selling_fees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellingFee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "percentage")
    @Min(value = 0)
    @Max(value = 100)
    @NotNull
    private Integer percentage;

    @Column(name = "by_default")
    @NotNull
    private Boolean byDefault=false;

    @Column(name = "description", nullable = false, unique = true)
    @NotBlank
    private String description;

}
