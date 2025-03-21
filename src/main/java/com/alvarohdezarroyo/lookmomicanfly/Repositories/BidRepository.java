package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid,String> {

    @Query("SELECT a FROM Bid a WHERE a.product.id = :productId AND a.size = :size AND a.active=true ORDER BY a.amount DESC LIMIT 1")
    Optional<Bid> getHighestBid(@Param("productId") String productId, @Param("size")Size size);

    @Query(value = "SELECT amount from bids WHERE size= :size AND product_id= :id AND active=true ORDER BY amount DESC limit 1",nativeQuery = true)
    Integer getHighestBidAmount(@Param("id") String id, @Param("size") String size);

}
