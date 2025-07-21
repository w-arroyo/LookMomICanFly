package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductSubcategory;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/products/categories")
public class ProductCategoryController {

    @GetMapping("/")
    public ResponseEntity<String[]> getProductCategories(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ProductCategory.getProductCategories());
    }

    @GetMapping("/subcategories/")
    public ResponseEntity<String[]> getCategorySubcategories(@RequestParam String category){
        GlobalValidator.checkIfAFieldIsEmpty(category);
        final ProductCategory productCategory= ProductValidator.checkIfProductCategoryExists(category);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ProductSubcategory.getSubcategoriesByCategory(category)
                        .toArray(String[]::new));
    }

}
