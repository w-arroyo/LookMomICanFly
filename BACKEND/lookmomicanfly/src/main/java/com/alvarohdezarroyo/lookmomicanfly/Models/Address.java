package com.alvarohdezarroyo.lookmomicanfly.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column (nullable = false)
    private Boolean active=true;

    @Column(name = "full_name",nullable = false)
    private byte [] fullName;

    @Column(nullable = false)
    private byte [] street;

    @Column(name = "zip_code", nullable = false)
    private byte [] zipCode;

    @Column(nullable = false)
    private byte [] city;

    @Column(nullable = false)
    private byte [] country;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
}
