package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ShippingOptionDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.SuccessfulRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.ShippingOption;
import com.alvarohdezarroyo.lookmomicanfly.Services.ShippingOptionService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ShippingOptionMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shipping-options")
public class ShippingOptionController {

    @Autowired
    private final ShippingOptionService shippingOptionService;

    public ShippingOptionController(ShippingOptionService shippingOptionService) {
        this.shippingOptionService = shippingOptionService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ShippingOptionDTO>> getAllShippingOption(){
        final List<ShippingOptionDTO> optionsDTO= shippingOptionService.getAllShippingOptions().stream().map(
                ShippingOptionMapper::toDTO
        ).toList();
        return ResponseEntity.status(HttpStatus.OK).body(optionsDTO);
    }

    @GetMapping("/get-price/")
    public ResponseEntity<SuccessfulRequestDTO> getShippingPriceByOptionId(@RequestParam String id){
        GlobalValidator.checkIfAFieldIsEmpty(id);
        final ShippingOption shippingOption=shippingOptionService.getShippingOptionById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessfulRequestDTO(shippingOption.getPrice()+"")
        );
    }

}
