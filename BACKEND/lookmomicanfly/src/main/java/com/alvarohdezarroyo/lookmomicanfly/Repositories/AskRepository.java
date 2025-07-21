package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AskRepository extends JpaRepository<Ask,String> {

    @Query("SELECT a FROM Ask a WHERE a.product.id = :productId AND a.size = :size AND a.active=true ORDER BY a.amount ASC LIMIT 1")
    Optional<Ask> getLowestAskForASizeOfAProduct(@Param("productId") String id, @Param("size") Size size);

    @Query(value = "SELECT amount from posts WHERE size= :size AND product_id= :productId AND active=true AND id IN (SELECT id from asks) ORDER BY amount ASC limit 1",nativeQuery = true)
    Optional<Integer> getLowestAskAmount(@Param("productId") String productId, @Param("size") String size);

    @Query(value = "SELECT ask from Ask ask WHERE ask.id IN (SELECT post.id from Post post WHERE post.user.id= :id AND post.active=true)")
    List<Ask> findAllUserAsks(@Param("id") String id);

    @Query(value = "SELECT a FROM Ask a WHERE a.product.id= :productId AND a.active= true ORDER BY a.amount ASC limit 1")
    Optional<Ask> getLowestAskOfAProduct(@Param("productId") String productId);

}
