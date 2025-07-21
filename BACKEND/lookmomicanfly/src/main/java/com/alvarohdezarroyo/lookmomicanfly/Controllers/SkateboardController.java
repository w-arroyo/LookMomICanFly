package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SkateboardDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Skateboard;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ColorService;
import com.alvarohdezarroyo.lookmomicanfly.Services.SkateboardService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products/skateboards")
public class SkateboardController {

    private final SkateboardService skateboardService;
    private final AuthService authService;
    private final ProductMapper productMapper;
    private final ColorService colorService;

    public SkateboardController(SkateboardService skateboardService, AuthService authService, ProductMapper productMapper, ColorService colorService) {
        this.skateboardService = skateboardService;
        this.authService = authService;
        this.productMapper = productMapper;
        this.colorService = colorService;
    }

    @GetMapping("/get/")
    public ResponseEntity<SkateboardDTO> getSkateboardDTOById(@RequestParam String id){
        GlobalValidator.checkIfAFieldIsEmpty(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productMapper.toSkateboardDTO(skateboardService.findSkateboardById(id)));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Skateboard>> saveSkateboardFromRequest(@RequestBody SkateboardDTO skateboardDTO){
        authService.checkIfAUserIsAdmin();
        GlobalValidator.checkIfRequestBodyIsEmpty(skateboardDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(skateboardDTO,skateboardDTO.getLength(),"length");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("success",
                        saveSkateboard(productMapper.toSkateboard(skateboardDTO))));
    }

    private Skateboard saveSkateboard(Skateboard skateboard){
        ProductValidator.checkIfCategoryIsCorrect(skateboard.getCategory(), ProductCategory.SKATEBOARDS);
        final Skateboard savedSkateboard=skateboardService.saveSkateboard(skateboard);
        colorService.saveProductColors(savedSkateboard.getColors(),savedSkateboard.getId());
        return savedSkateboard;
    }

}
