package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.PhoneNumberDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.PhoneNumberFormatDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.PhoneFormat;
import com.alvarohdezarroyo.lookmomicanfly.Models.PhoneNumber;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.SavePhoneNumberRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.PhoneNumberService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.PhoneNumberMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.PhoneNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/phone-numbers")
public class PhoneNumberController {

    @Autowired
    private final PhoneNumberService phoneNumberService;
    private final PhoneNumberMapper phoneNumberMapper;
    private final AuthService authService;

    public PhoneNumberController(PhoneNumberService phoneNumberService, PhoneNumberMapper phoneNumberMapper, AuthService authService) {
        this.phoneNumberService = phoneNumberService;
        this.phoneNumberMapper = phoneNumberMapper;
        this.authService = authService;
    }

    @GetMapping("/user/")
    public ResponseEntity<Map<String, PhoneNumberDTO>> getUserPhoneNumber(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final PhoneNumber phoneNumber=phoneNumberService.getUserPhoneNumber(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("phoneNumber",
                        PhoneNumberMapper.toDTO(phoneNumber)));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, PhoneNumberDTO>> savePhoneNumber(@RequestBody SavePhoneNumberRequestDTO savePhoneNumberRequestDTO){
        PhoneNumberValidator.checkPhoneNumberFields(savePhoneNumberRequestDTO);
        authService.checkFraudulentRequest(savePhoneNumberRequestDTO.getUserId());
        PhoneNumberValidator.validatePhoneNumber(savePhoneNumberRequestDTO);
        phoneNumberService.deactivateAllUserPhoneNumbers(savePhoneNumberRequestDTO.getUserId());
        final PhoneNumber phoneNumber=phoneNumberService.savePhoneNumber(
                phoneNumberMapper.toPhoneNumber(savePhoneNumberRequestDTO)
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("success",
                        PhoneNumberMapper.toDTO(phoneNumber)));
    }

    @PutMapping("/deactivate/")
    public ResponseEntity<String> deactivateUserNumbers(@RequestParam String user){
        GlobalValidator.checkIfAFieldIsEmpty(user);
        authService.checkFraudulentRequest(user);
        phoneNumberService.deactivateAllUserPhoneNumbers(user);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @GetMapping("/formats")
    public ResponseEntity<Map<String,PhoneNumberFormatDTO[]>> getPhoneNumberFormats(){
        final List<PhoneFormat> formats= Arrays.stream(PhoneFormat.values()).toList();
        final PhoneNumberFormatDTO[] array =formats.stream().map(PhoneNumberMapper::toFormatDTO).toArray(size -> new PhoneNumberFormatDTO[formats.size()]);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("formats",
                        array));
    }

}
