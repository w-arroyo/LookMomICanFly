package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ClothingDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Clothing;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ClothingService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ColorService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products/clothing")
public class ClothingController {

    @Autowired
    private final ClothingService clothingService;
    private final AuthService authService;
    private final ProductMapper productMapper;
    private final ColorService colorService;

    public ClothingController(ClothingService clothingService, AuthService authService, ProductMapper productMapper, ColorService colorService) {
        this.clothingService = clothingService;
        this.authService = authService;
        this.productMapper = productMapper;
        this.colorService = colorService;
    }

    @GetMapping("/get/")
    public ResponseEntity<ClothingDTO> getClothingDTOById(@RequestParam String id){
        GlobalValidator.checkIfAFieldIsEmpty(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productMapper.toClothingDTO(clothingService.getClothingById(id)));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Clothing>> saveClothingProductFromRequest(@RequestBody ClothingDTO clothingDTO){
        authService.checkIfAUserIsAdmin();
        GlobalValidator.checkIfRequestBodyIsEmpty(clothingDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(clothingDTO, clothingDTO.getSeason(), "season");
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",saveClothing(productMapper.toClothing(clothingDTO))));
    }

    private Clothing saveClothing(Clothing clothing){
        ProductValidator.checkIfCategoryIsCorrect(clothing.getCategory(), ProductCategory.CLOTHING);
        final Clothing savedClothing=clothingService.saveClothing(clothing);
        colorService.saveProductColors(savedClothing.getColors(), savedClothing.getId());
        return savedClothing;
    }

}
