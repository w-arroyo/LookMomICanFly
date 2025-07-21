package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.SuccessfulRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.DeactivateAddressRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.AddressService;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.AddressMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.AddressValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private final AddressService addressService;
    private final AuthService authService;
    private final AddressMapper addressMapper;

    public AddressController(AddressService addressService, AuthService authService, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.authService = authService;
        this.addressMapper = addressMapper;
    }

    @PostMapping("/save")
    public ResponseEntity <SuccessfulRequestDTO> saveAddress(@RequestBody AddressDTO address) throws Exception {
        GlobalValidator.checkIfRequestBodyIsEmpty(address);
        AddressValidator.checkIfFieldsAreEmpty(address);
        authService.checkIfAUserIsLoggedIn();
        final Address addressToSave= addressMapper.toEntity(address);
        addressService.saveAddress(addressToSave);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessfulRequestDTO("Address successfully created."));
    }

    @PutMapping("/deactivate")
    public ResponseEntity<SuccessfulRequestDTO> deactivateAddress(@RequestBody DeactivateAddressRequestDTO request){
        GlobalValidator.checkIfRequestBodyIsEmpty(request);
        GlobalValidator.checkIfTwoFieldsAreEmpty(request.getId(), request.getUserId());
        authService.checkFraudulentRequest(request.getUserId());
        addressService.deactivateAddress(request.getId(), request.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessfulRequestDTO("Address successfully removed.")
        );
    }

}
