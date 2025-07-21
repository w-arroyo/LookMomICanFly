package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    
    List<Product> findAllByCategory(ProductCategory category);

    @Query(value = "SELECT category from products where id= :id", nativeQuery = true)
    ProductCategory getProductCategoryByProductId(@Param("id") String id);

    @Query(value = "SELECT COUNT(*) FROM users_favorite_products WHERE user_id= :userId AND product_id= :productId", nativeQuery = true)
    int checkIfAUserLikesAProduct(@Param("userId") String userId, @Param("productId") String productId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users_favorite_products WHERE user_id= :userId AND product_id= :productId", nativeQuery = true)
    void unlikeProduct(@Param("userId") String userId, @Param("productId") String productId);

    @Modifying
    @Transactional
    @Query(value = "INSERT IGNORE INTO users_favorite_products VALUES ( :productId , :userId )", nativeQuery = true)
    void likeProduct(@Param("userId") String userId, @Param("productId") String productId);

    @Query(value = "SELECT p FROM Product p JOIN p.users u WHERE u.id = :id")
    List<Product> getUserLikedProducts(@Param("id") String id);

    @Query(value = "SELECT product FROM Product product WHERE name ILIKE %:name%")
    List<Product> findProductsByName(@Param("name") String name);

    @Query(value = "SELECT po.product_id FROM transactions t JOIN sales s ON s.id = t.sale_id JOIN asks a ON a.id = s.ask_id JOIN posts po ON po.id = a.id WHERE t.date >= DATE_SUB(NOW(), INTERVAL 6 MONTH) GROUP BY po.product_id ORDER BY COUNT(*) DESC LIMIT 50", nativeQuery = true)
    List<String> findBestSellingProductIds();

    @Query("SELECT p FROM Product p WHERE p.id IN :ids")
    List<Product> findBest50SellingProducts(@Param("ids") List<String> idList);

    @Query(value = "SELECT DISTINCT release_year FROM products ORDER BY release_year DESC", nativeQuery = true)
    List<Integer> getDifferentProductYears();

    @Query(value = "SELECT DISTINCT(name) from colors WHERE id IN(SELECT color_id from colors_products) ORDER BY name", nativeQuery = true)
    List<String> getAllProductColors();

    @Query(value = "SELECT DISTINCT name FROM manufacturers WHERE id IN (SELECT manufacturer_id from products) ORDER BY name", nativeQuery = true)
    List<String> getDifferentManufacturers();

    @Query("SELECT p FROM Product p WHERE " +
            "(:yearsEmpty = true OR p.releaseYear IN :years) " +
            "AND (:subcategoriesEmpty = true OR p.subcategory IN :subcategories) " +
            "AND (:manufacturersEmpty = true OR p.manufacturer.name IN :manufacturers) " +
            "AND (:colorsEmpty = true OR EXISTS (" +
            "SELECT 1 FROM p.colors c WHERE c.name IN :colors))")
    List<Product> filterProducts(@Param("years") List<Integer> years, @Param("manufacturers") List<String> manufacturers, @Param("subcategories") List<String> subcategories, @Param("colors") List<String> colors, @Param("yearsEmpty") boolean yearsEmpty, @Param("manufacturersEmpty") boolean manufacturersEmpty, @Param("subcategoriesEmpty") boolean subcategoriesEmpty, @Param("colorsEmpty") boolean colorsEmpty);

}
