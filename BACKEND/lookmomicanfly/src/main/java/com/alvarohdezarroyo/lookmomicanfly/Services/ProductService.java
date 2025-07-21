package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Product;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ProductRepository;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.FilterProductRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProductById(String id){
        return productRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Product id does not exist.")
        );
    }

    public ProductCategory getProductCategoryById(String id){
        try {
            return productRepository.getProductCategoryByProductId(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Product> findProductsByName(String name){
        return productRepository.findProductsByName(name);
    }

    public List<Product> findAllProducts(){
        final List<Product> allProducts = productRepository.findAll();
        Collections.shuffle(allProducts);
        return allProducts;
    }

    public List<Product> findAllProductsByCategory(ProductCategory category){
        final List<Product> products = productRepository.findAllByCategory(category);
        Collections.shuffle(products);
        return products;
    }

    public List<Product> findUserLikedProducts(String userId){
        return productRepository.getUserLikedProducts(userId);
    }

    public boolean checkIfUserLikesAProduct(String userId, String productId){
        return productRepository.checkIfAUserLikesAProduct(userId,productId)>0;
    }

    @Transactional
    public void likeProduct(String userId, String productId){
        productRepository.likeProduct(userId,productId);
    }

    @Transactional
    public void unlikeProduct(String userId, String productId){
        productRepository.unlikeProduct(userId,productId);
    }

    public List<Product> get50BestSellingProductsDuringLastSixMonths(){
        final List<String> ids=productRepository.findBestSellingProductIds();
        return productRepository.findBest50SellingProducts(ids);
    }

    public Integer[] getAllDifferentProductReleaseYears(){
        return productRepository.getDifferentProductYears()
                .toArray(Integer[]::new);
    }

    public String[] getAllProductColors(){
        return productRepository.getAllProductColors()
                .toArray(String[]::new);
    }

    public String[] getAllProductManufacturers(){
        return productRepository.getDifferentManufacturers()
                .toArray(String[]::new);
    }

    public List<Product> getFilteredProducts(FilterProductRequestDTO request){
        return productRepository.filterProducts(request.getYears(),request.getManufacturers(),request.getSubcategories(),request.getColors(),
                request.getYears().isEmpty(),request.getManufacturers().isEmpty(),request.getSubcategories().isEmpty(),request.getColors().isEmpty());
    }

}
