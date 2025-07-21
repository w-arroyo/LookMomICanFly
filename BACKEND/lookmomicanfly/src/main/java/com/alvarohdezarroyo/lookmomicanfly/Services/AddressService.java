package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Address saveAddress(Address address){
        return addressRepository.save(address);
    }

    @Transactional
    public void deactivateAllUserAddresses(String id){
        addressRepository.deactivateAllUserAddresses(id);
    }

    @Transactional
    public void deactivateAddress(String id, String userId){
        if(!getAddressById(id)
                .getUserId().getId().equals(userId))
            throw new FraudulentRequestException("Address does not belong to you.");
        if(addressRepository.deactivateAddress(id)<1)
            throw new RuntimeException("Server error. Please Try again later.");
    }

    public Address getAddressById(String id){
        return addressRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Address id does not exist.")
        );
    }

}
