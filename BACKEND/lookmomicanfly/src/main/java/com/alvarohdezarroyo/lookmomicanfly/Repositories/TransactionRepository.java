package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,String> {

    @Query(value = "SELECT order_id from transactions WHERE sale_id= :id", nativeQuery = true)
    Optional<String> getOrderIdFromTransaction(@Param("id") String id);

    @Query(value = "SELECT date from transactions WHERE sale_id= :id OR order_id= :id",nativeQuery = true)
    LocalDateTime getTransactionDate(@Param("id") String id);

}
