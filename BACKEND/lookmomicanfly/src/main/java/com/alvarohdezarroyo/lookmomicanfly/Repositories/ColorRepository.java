package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.Color;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color,String> {

    Optional<Color> findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "INSERT IGNORE INTO colors_products VALUES (:productId,:colorId)", nativeQuery = true)
    void insertProductColors(@Param("productId") String productId, @Param("colorId") String colorId);

}
