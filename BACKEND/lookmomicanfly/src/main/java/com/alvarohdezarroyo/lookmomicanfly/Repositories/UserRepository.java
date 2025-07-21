package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, String> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET active=false WHERE id =:id", nativeQuery = true)
    int deactivateUserAccount(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET password= :password WHERE id=:id", nativeQuery = true)
    int changeUserPassword(@Param("id") String id ,@Param("password") String password);

    @Modifying
    @Transactional
    @Query(value ="UPDATE users SET email = :newEmail WHERE id = :id", nativeQuery = true)
    int changeUserEmail(@Param("id") String id, @Param("newEmail") String newEmail);

}
