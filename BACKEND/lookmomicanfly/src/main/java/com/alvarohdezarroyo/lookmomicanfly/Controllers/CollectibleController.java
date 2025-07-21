package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.CollectibleDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Collectible;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.CollectibleService;
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
@RequestMapping("/api/products/collectibles")
public class CollectibleController {

    @Autowired
    private final CollectibleService collectibleService;
    private final AuthService authService;
    private final ProductMapper productMapper;
    private final ColorService colorService;

    public CollectibleController(CollectibleService collectibleService, AuthService authService, ProductMapper productMapper, ColorService colorService) {
        this.collectibleService = collectibleService;
        this.authService = authService;
        this.productMapper = productMapper;
        this.colorService = colorService;
    }

    @GetMapping("/get/")
    public ResponseEntity<CollectibleDTO> getCollectibleDTOById(@RequestParam String id){
        GlobalValidator.checkIfAFieldIsEmpty(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productMapper.toCollectibleDTO(collectibleService.getCollectibleById(id)));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Collectible>> saveCollectibleFromRequest(@RequestBody CollectibleDTO collectibleDTO){
        authService.checkIfAUserIsAdmin();
        GlobalValidator.checkIfRequestBodyIsEmpty(collectibleDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(collectibleDTO,collectibleDTO.getCollectionName(),"collection_name");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("success",
                        saveCollectible(productMapper.toCollectible(collectibleDTO))));
    }

    private Collectible saveCollectible(Collectible collectible){
        ProductValidator.checkIfCategoryIsCorrect(collectible.getCategory(), ProductCategory.COLLECTIBLES);
        final Collectible savedCollectible=collectibleService.saveCollectible(collectible);
        colorService.saveProductColors(savedCollectible.getColors(), savedCollectible.getId());
        return savedCollectible;
    }

}
