package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AccessoryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Accessory;
import com.alvarohdezarroyo.lookmomicanfly.Services.AccessoryService;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
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
@RequestMapping("/api/products/accessories")
public class AccessoryController {

    @Autowired
    private final AccessoryService accessoryService;
    private final AuthService authService;
    private final ProductMapper productMapper;
    private final ColorService colorService;

    public AccessoryController(AccessoryService accessoryService, AuthService authService, ProductMapper productMapper, ColorService colorService) {
        this.accessoryService = accessoryService;
        this.authService = authService;
        this.productMapper = productMapper;
        this.colorService = colorService;
    }

    @GetMapping("/get/")
    public ResponseEntity<AccessoryDTO> getAccessoryDTOById(@RequestParam String id){
        GlobalValidator.checkIfAFieldIsEmpty(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productMapper.toAccessoryDTO(accessoryService.getAccessoryById(id)));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Accessory>> saveAccessoryFromRequest(@RequestBody AccessoryDTO accessoryDTO){
        authService.checkIfAUserIsAdmin();
        GlobalValidator.checkIfRequestBodyIsEmpty(accessoryDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(accessoryDTO,accessoryDTO.getMaterial(),"material");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("success",
                        saveAccessory(productMapper.toAccessory(accessoryDTO))));
    }

    private Accessory saveAccessory(Accessory accessory){
        ProductValidator.checkIfCategoryIsCorrect(accessory.getCategory(), ProductCategory.ACCESSORIES);
        final Accessory savedAccessory=accessoryService.saveAccessory(accessory);
        colorService.saveProductColors(accessory.getColors(), accessory.getId());
        return savedAccessory;
    }

}
