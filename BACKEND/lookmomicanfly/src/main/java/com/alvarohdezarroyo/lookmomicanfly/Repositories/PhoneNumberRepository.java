package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.PhoneNumber;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber,String> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE phone_numbers SET active=false WHERE user_id= :id", nativeQuery = true)
    void deactivateAllUserPhones(@Param("id") String id);

    @Query(value = "SELECT * from phone_numbers WHERE user_id= :id AND active=true", nativeQuery = true)
    Optional<PhoneNumber> getUserPhoneNumber(@Param("id") String id);

}
