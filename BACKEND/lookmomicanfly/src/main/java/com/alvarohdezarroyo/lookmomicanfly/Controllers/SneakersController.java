package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SneakersDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Sneakers;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ColorService;
import com.alvarohdezarroyo.lookmomicanfly.Services.SneakersService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products/sneakers")
public class SneakersController {

    @Autowired
    private final SneakersService sneakersService;
    private final AuthService authService;
    private final ProductMapper productMapper;
    private final ColorService colorService;

    public SneakersController(SneakersService sneakersService, AuthService authService, ProductMapper productMapper, ColorService colorService) {
        this.sneakersService = sneakersService;
        this.authService = authService;
        this.productMapper = productMapper;
        this.colorService = colorService;
    }

    @GetMapping("/get/")
    public ResponseEntity<SneakersDTO> getSneakersById(@RequestParam String id){
        GlobalValidator.checkIfAFieldIsEmpty(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productMapper.toSneakersDTO(sneakersService.getSneakersById(id)));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Sneakers>> saveSneakersFromRequest(@RequestBody SneakersDTO sneakersDTO){
        authService.checkIfAUserIsAdmin();
        GlobalValidator.checkIfRequestBodyIsEmpty(sneakersDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(sneakersDTO,sneakersDTO.getSku(),"sku");
        Sneakers sneakers= productMapper.toSneakers(sneakersDTO);
        ProductValidator.checkIfCategoryIsCorrect(sneakers.getCategory(), ProductCategory.SNEAKERS);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("success",
                        saveSneakers(productMapper.toSneakers(sneakersDTO))));
    }

    private Sneakers saveSneakers(Sneakers sneakers){
        ProductValidator.checkIfCategoryIsCorrect(sneakers.getCategory(), ProductCategory.SNEAKERS);
        final Sneakers savedSneakers=sneakersService.saveSneakers(sneakers);
        colorService.saveProductColors(savedSneakers.getColors(),savedSneakers.getId());
        return savedSneakers;
    }

}
