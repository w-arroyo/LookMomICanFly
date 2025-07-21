package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tracking_numbers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackingNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "code")
    @NotNull
    @NotBlank
    private String code;

    @Column(name = "used")
    @NotNull
    private Boolean used;

    @Column(name = "date")
    @NotNull
    private LocalDateTime date= LocalDateTime.now();

    public TrackingNumber(String code, boolean used){
        this.code=code;
        this.used=used;
    }

}
