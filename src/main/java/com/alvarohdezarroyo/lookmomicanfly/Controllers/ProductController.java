package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ProductSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.ProductAlreadyLikedException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Product;
import com.alvarohdezarroyo.lookmomicanfly.Services.*;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final AuthService authService;

    public ProductController(ProductService productService, ProductMapper productMapper, AuthService authService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.authService = authService;
    }

    @GetMapping("/get/all-summary")
    public ResponseEntity<List<ProductSummaryDTO>> getAllProductsSummary(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productMapper.toSummaryList(productService.findAllProducts()));
    }

    @GetMapping("/get/all-summary-by-category/")
    public ResponseEntity<List<ProductSummaryDTO>> getCategorySummary(@RequestParam String category){
        GlobalValidator.checkIfAFieldIsEmpty(category);
        List<Product> productList = productService.findAllProductsByCategory(ProductValidator.checkIfProductCategoryExists(category));
        return ResponseEntity.status(HttpStatus.OK)
                .body(productMapper.toSummaryList(productList));
    }

    @GetMapping("/favorites/list/")
    public ResponseEntity<List<ProductSummaryDTO>> getUserLikedProducts(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final List<Product> list= productService.findUserLikedProducts(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productMapper.toSummaryList(list));
    }

    @GetMapping("/favorites/check/")
    public ResponseEntity<Boolean> checkIfUserLikedAProduct(@RequestParam String userId, @RequestParam String productId){
        checkLikingProducts(userId,productId);
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.checkIfUserLikesAProduct(userId,productId)
        );
    }

    @PostMapping("/favorites/like/")
    public ResponseEntity<String> likeProduct(@RequestParam String userId, @RequestParam String productId){
        checkLikingProducts(userId,productId);
        if(!productService.checkIfUserLikesAProduct(userId,productId))
            throw new ProductAlreadyLikedException("You already like this product.");
        productService.likeProduct(userId,productId);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @PutMapping("/favorites/unlike/")
    public ResponseEntity<String> unlikeProduct(@RequestParam String userId, @RequestParam String productId){
        checkLikingProducts(userId,productId);
        if(productService.checkIfUserLikesAProduct(userId,productId))
            throw new ProductAlreadyLikedException("You already do not like this product.");
        productService.unlikeProduct(userId,productId);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @GetMapping("/find/")
    public ResponseEntity <List<ProductSummaryDTO>> findProducts(@RequestParam String name){
        GlobalValidator.checkIfAFieldIsEmpty(name);
        final List<Product> products=productService.findProductsByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productMapper.toSummaryList(products));
    }

    @GetMapping("/best-sellers")
    public ResponseEntity<List<ProductSummaryDTO>> findBestSellingProducts(){
        final List<Product> products=productService.get50BestSellingProductsDuringLastSixMonths();
        return ResponseEntity.status(HttpStatus.OK)
                .body(productMapper.toSummaryList(products));
    }

    @GetMapping("/get/years")
    public ResponseEntity<Integer[]> getDifferentYears(){
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.getAllDifferentProductReleaseYears()
        );
    }

    @GetMapping("/get/colors")
    public ResponseEntity<String[]> getDifferentColors(){
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.getAllProductColors()
        );
    }

    @GetMapping("/get/manufacturers")
    public ResponseEntity<String[]> getDifferentManufacturers(){
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.getAllProductManufacturers()
        );
    }

    @GetMapping("/get/sizes/")
    public ResponseEntity<List<String>> getProductSizeChart(@RequestParam String productId){
        GlobalValidator.checkIfAFieldIsEmpty(productId);
        final Product product=productService.findProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(
                Size.getSizesByCategory(product.getCategory())
                        .stream().map(Size::getValue).toList()
        );
    }

    private void checkLikingProducts(String userId, String productId){
        GlobalValidator.checkIfTwoFieldsAreEmpty(userId,productId);
        authService.checkFraudulentRequest(userId);
    }

}
