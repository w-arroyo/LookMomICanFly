package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.AddressRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.AddressMapper;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private final AddressRepository addressRepository;
    private final UserValidator userValidator;

    public AddressService(AddressRepository addressRepository, UserValidator userValidator) {
        this.addressRepository = addressRepository;
        this.userValidator = userValidator;
    }

    public Address saveAddress(AddressDTO addressDTO) {
        try {
            return addressRepository.save(AddressMapper.toEntity(addressDTO, userValidator.returnUserById(addressDTO.getUserId())));
        }
        catch (EntityNotFoundException ex){
            throw new EntityNotFoundException("User Id not found.");
        } catch (Exception e) {
            throw new RuntimeException("Server error. Please Try again later.");
        }
    }

    public void deactivateAddress(String id, String userId){
        try{
            GlobalValidator.checkFraudulentRequest(userId, addressRepository.findById(id).orElseThrow(
                    ()->new EntityNotFoundException("Address id not found")).getUserId().getId());
            if(addressRepository.deactivateAddress(id)<1)
                throw new RuntimeException("Server error. Please Try again later.");
        } catch (FraudulentRequestException e) {
            throw new FraudulentRequestException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Server error.");
        }
    }

}
