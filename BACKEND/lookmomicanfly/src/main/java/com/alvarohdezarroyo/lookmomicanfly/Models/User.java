package com.alvarohdezarroyo.lookmomicanfly.Models;

import com.alvarohdezarroyo.lookmomicanfly.Enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table (name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Email is mandatory.")
    @Email
    @Column (nullable = false, unique = true)
    @Size(min = 10, max = 220, message = "Email length is invalid.")
    private String email;

    @Column (nullable = false)
    @NotBlank
    @Size(min = 5, max = 220, message = "Password length is invalid.")
    private String password;

    @Column (nullable = false)
    private byte [] name;

    @Column (nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active = true;

    @Column (name = "registration_date",nullable = false, updatable = false)
    private LocalDateTime registrationDate= LocalDateTime.now();

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany (mappedBy = "userId", fetch = FetchType.LAZY)
    private List<Address> addresses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<BankAccount> bankAccounts;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_favorite_products",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> favoriteProducts;

}
