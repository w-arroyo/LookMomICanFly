package com.alvarohdezarroyo.lookmomicanfly.Utils.Validators;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressValidator {

    public static void checkIfFieldsAreEmpty(AddressDTO addressDTO){
        List<String> emptyFields=new ArrayList<>();
        if(addressDTO.getUserId()==null || addressDTO.getUserId().isBlank()){
            emptyFields.add("userId");
        }
        if(addressDTO.getFullName()==null || addressDTO.getFullName().isBlank()){
            emptyFields.add("fullName");
        }
        if(addressDTO.getStreet()==null || addressDTO.getStreet().isBlank()){
            emptyFields.add("street");
        }
        if(addressDTO.getCity()==null || addressDTO.getCity().isBlank()){
            emptyFields.add("city");
        }
        if(addressDTO.getZipCode()==null || addressDTO.getZipCode().isBlank()){
            emptyFields.add("zipCode");
        }
        if(addressDTO.getCountry()==null || addressDTO.getCountry().isBlank()){
            emptyFields.add("country");
        }
        if(!emptyFields.isEmpty())
            throw new EmptyFieldsException(emptyFields);
    }

    public static void checkIfAddressBelongsToAUser(String userId, Address address){
        if(!address.getUserId().getId().equalsIgnoreCase(userId))
            throw new FraudulentRequestException("Address does not belong to the user.");
    }

}
