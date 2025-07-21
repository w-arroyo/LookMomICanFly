package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Config.AppConfig;
import com.alvarohdezarroyo.lookmomicanfly.DTO.SellingFeeDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.SuccessfulRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.SellingFeeService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.SellingFeeMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fees")
public class FeeController {

    @Autowired
    private final SellingFeeService sellingFeeService;

    public FeeController(SellingFeeService sellingFeeService) {
        this.sellingFeeService = sellingFeeService;
    }

    @GetMapping("/level/")
    public ResponseEntity<SellingFeeDTO> getUserLevelSellingFee(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SellingFeeMapper.toDTO(
                                sellingFeeService.selectFeeByNumberSales(userId)
                        ));
    }

    @GetMapping("/default/")
    public ResponseEntity<SellingFeeDTO> getCurrentSellingFee(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SellingFeeMapper.toDTO(
                        sellingFeeService.checkIfThereIsADefaultFee(userId)
                ));
    }

    @GetMapping("/shipping")
    public ResponseEntity<SuccessfulRequestDTO> getSellingShippingFee(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessfulRequestDTO(AppConfig.getSellingShipping()+"")
        );
    }

    @PutMapping("/remove-default")
    public ResponseEntity<SuccessfulRequestDTO> removeSellingFeeOffer(){
        sellingFeeService.deactivateCurrentSellingFeeOffers();
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessfulRequestDTO("Removed")
        );
    }

    @PostMapping("/save-default")
    public ResponseEntity<SuccessfulRequestDTO> saveNewDefaultSellingFee(@RequestBody SellingFeeDTO sellingFeeDTO){
        GlobalValidator.checkIfRequestBodyIsEmpty(sellingFeeDTO);
        GlobalValidator.checkIfAFieldIsEmpty(sellingFeeDTO.getDescription());
        GlobalValidator.checkIfANumberFieldIsValid(sellingFeeDTO.getPercentage());
        sellingFeeService.saveSellingFeeOffer(sellingFeeDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessfulRequestDTO("Created")
        );
    }

}
