package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.FootballDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Football;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ColorService;
import com.alvarohdezarroyo.lookmomicanfly.Services.FootballService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products/football")
public class FootballController {

    @Autowired
    private final FootballService footballService;
    private final AuthService authService;
    private final ProductMapper productMapper;
    private final ColorService colorService;

    public FootballController(FootballService footballService, AuthService authService, ProductMapper productMapper, ColorService colorService) {
        this.footballService = footballService;
        this.authService = authService;
        this.productMapper = productMapper;
        this.colorService = colorService;
    }

    @GetMapping("/get/")
    public ResponseEntity<FootballDTO> getFootballDTOById(@RequestParam String id){
        GlobalValidator.checkIfAFieldIsEmpty(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productMapper.toFootballDTO(footballService.getFootballById(id)));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Football>> saveFootballFromRequest(@RequestBody FootballDTO footballDTO){
        authService.checkIfAUserIsAdmin();
        GlobalValidator.checkIfRequestBodyIsEmpty(footballDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(footballDTO,footballDTO.getScope(),"scope");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("success",
                        saveFootball(productMapper.toFootball(footballDTO))));
    }

    private Football saveFootball(Football football){
        ProductValidator.checkIfCategoryIsCorrect(football.getCategory(), ProductCategory.FOOTBALL);
        final Football savedFootball=footballService.saveFootball(football);
        colorService.saveProductColors(savedFootball.getColors(), savedFootball.getId());
        return savedFootball;
    }

}
