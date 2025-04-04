package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/products/categories")
public class ProductCategoryController {

    @GetMapping("/")
    public ResponseEntity<Map<String,String[]>> getProductCategories(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("categories",
                        ProductCategory.getProductCategories()));
    }

}
