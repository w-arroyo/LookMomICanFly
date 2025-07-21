package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductSubcategory;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products/subcategories")
public class ProductSubcategoryController {

    @GetMapping("/")
    public ResponseEntity<Map<String, List<String>>> getProductCategorySubcategories(@RequestParam String category){
        final ProductCategory productCategory= ProductValidator.checkIfProductCategoryExists(category);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("subcategories",
                        ProductSubcategory.getSubcategoriesByCategory(productCategory.name())));
    }

}
