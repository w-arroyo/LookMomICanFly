package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository <Address, String> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE addresses SET active=false WHERE id= :id",nativeQuery = true)
    int deactivateAddress(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE addresses SET active=false WHERE user_id= :id", nativeQuery = true)
    void deactivateAllUserAddresses(@Param("id") String id);
}
