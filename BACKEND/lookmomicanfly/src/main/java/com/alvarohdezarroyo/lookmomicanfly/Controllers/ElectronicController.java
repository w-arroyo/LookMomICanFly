package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ElectronicDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Electronic;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ColorService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ElectronicService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products/electronics")
public class ElectronicController {

    @Autowired
    private final ElectronicService electronicService;
    private final AuthService authService;
    private final ProductMapper productMapper;
    private final ColorService colorService;

    public ElectronicController(ElectronicService electronicService, AuthService authService, ProductMapper productMapper, ColorService colorService) {
        this.electronicService = electronicService;
        this.authService = authService;
        this.productMapper = productMapper;
        this.colorService = colorService;
    }

    @GetMapping("/get/")
    public ResponseEntity<ElectronicDTO> getElectronicDTOById(@RequestParam String id){
        GlobalValidator.checkIfAFieldIsEmpty(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productMapper.toElectronicDTO(electronicService.getElectronicById(id)));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Electronic>> saveElectronicFromRequest(@RequestBody ElectronicDTO electronicDTO){
        authService.checkIfAUserIsAdmin();
        GlobalValidator.checkIfRequestBodyIsEmpty(electronicDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(electronicDTO,electronicDTO.isCaution()+"","caution");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("success",
                        saveElectronic(productMapper.toElectronic(electronicDTO))));
    }

    private Electronic saveElectronic(Electronic electronic){
        ProductValidator.checkIfCategoryIsCorrect(electronic.getCategory(), ProductCategory.ELECTRONICS);
        final Electronic savedElectronic=electronicService.saveElectronic(electronic);
        colorService.saveProductColors(savedElectronic.getColors(), savedElectronic.getId());
        return savedElectronic;
    }

}
