package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Services.TrackingNumberService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tracking")
public class TrackingNumberController {

    @Autowired
    private final TrackingNumberService trackingNumberService;

    public TrackingNumberController(TrackingNumberService trackingNumberService) {
        this.trackingNumberService = trackingNumberService;
    }

    @GetMapping("/sale-tracking-amount/")
    public ResponseEntity<Map<String,Integer>> getSaleAmountOfTrackingNumbers(@RequestParam String saleId){
        GlobalValidator.checkIfAFieldIsEmpty(saleId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("number",
                        trackingNumberService.getSaleAmountOfTrackingNumbers(saleId)));
    }

}
