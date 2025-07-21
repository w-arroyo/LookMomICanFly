package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.TrackingNumber;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TrackingNumberRepository extends JpaRepository<TrackingNumber,String> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE tracking_numbers SET used=true where ID = :id", nativeQuery = true)
    int useTrackingNumber(@Param("id")String id);

    @Modifying
    @Transactional
    @Query(value = "INSERT IGNORE INTO tracking_and_sales VALUES (:trackingId,:saleId)", nativeQuery = true)
    void insertIntoSalesTrackingTable(@Param("trackingId") String trackingId, @Param("saleId") String saleId);

    @Modifying
    @Transactional
    @Query(value = "INSERT IGNORE INTO tracking_and_orders VALUES (:trackingId,:orderId)", nativeQuery = true)
    void insertIntoOrdersTrackingTable(@Param("trackingId") String trackingId, @Param("orderId") String orderId);

    @Query(value = "SELECT * FROM tracking_numbers WHERE id IN (SELECT tracking_number_id FROM tracking_and_sales WHERE sale_id = :id) ORDER BY date DESC LIMIT 1", nativeQuery = true)
    Optional<TrackingNumber> getSaleTrackingNumber(@Param("id") String id);

    @Query(value = "SELECT count(*) from tracking_and_sales WHERE sale_id= :id", nativeQuery = true)
    int getSaleAmountOfTrackingNumbers(@Param("id") String id);

    @Query(value = "SELECT code FROM tracking_numbers WHERE id IN (SELECT tracking_number_id FROM tracking_and_orders WHERE order_id = :id) LIMIT 1", nativeQuery = true)
    Optional<String> getOrderTrackingNumber(@Param("id") String id);
}
