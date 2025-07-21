package com.alvarohdezarroyo.lookmomicanfly.Utils.Validators;

import com.alvarohdezarroyo.lookmomicanfly.Enums.PhoneFormat;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.SavePhoneNumberRequestDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PhoneNumberValidator {

    public static void validatePhoneNumber(SavePhoneNumberRequestDTO phoneNumber){
        final PhoneFormat format=PhoneFormat.checkIfPrefixExists(phoneNumber.getPrefix());
        try{
            Integer.parseInt(phoneNumber.getDigits());
        }
        catch(NumberFormatException ex){
            throw new NumberFormatException("Phone number must be composed by only numbers.");
        }
        if(phoneNumber.getDigits().length()!= format.getNumberLength())
            throw new IllegalArgumentException("Number with prefix +"+phoneNumber.getPrefix()+" must have "+format.getNumberLength()+" digits.");
    }

    public static void checkPhoneNumberFields(SavePhoneNumberRequestDTO phoneNumber){
        final List<String> emptyFields=new ArrayList<>();
        if(phoneNumber.getPrefix()==null || phoneNumber.getPrefix().trim().isEmpty())
            emptyFields.add("prefix");
        if(phoneNumber.getDigits()==null || phoneNumber.getDigits().trim().isEmpty())
            emptyFields.add("digits");
        if(phoneNumber.getUserId()==null || phoneNumber.getUserId().trim().isEmpty())
            emptyFields.add("userId");
        if(!emptyFields.isEmpty())
            throw new EmptyFieldsException(emptyFields);
    }

}
