package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.SellingFee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SellingFeeRepository extends JpaRepository<SellingFee,String> {

    Optional<SellingFee> findByDescription(String s);

    @Modifying
    @Transactional
    @Query(value = "UPDATE selling_fees SET by_default=false WHERE by_default=true", nativeQuery = true)
    void deactivateCurrentSellingFeeOffers();

    Optional<SellingFee> findByByDefaultTrue();

}
