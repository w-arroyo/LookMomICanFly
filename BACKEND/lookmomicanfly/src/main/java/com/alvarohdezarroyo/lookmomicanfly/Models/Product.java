package com.alvarohdezarroyo.lookmomicanfly.Models;

import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductSubcategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    @Size(min = 10, max = 220, message = "Name length is invalid.")
    private String name;

    @Column(name = "release_year", nullable = false)
    @Min(value = 1950) // value must be constant, so I can't use a max validator.
    private Integer releaseYear;

    @Column(name = "active", nullable = false)
    private Boolean active=true;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Column(name = "subcategory", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductSubcategory subcategory;

    @ManyToOne(fetch = FetchType.EAGER) // loads up the manufacturer of the product automatically
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "colors_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    private List<Color> colors;

    @ManyToMany(mappedBy = "favoriteProducts", fetch = FetchType.LAZY)
    private List<User> users;

}
