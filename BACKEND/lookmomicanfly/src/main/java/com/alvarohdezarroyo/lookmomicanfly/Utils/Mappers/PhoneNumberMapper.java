package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.PhoneNumberDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.PhoneNumberFormatDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.PhoneFormat;
import com.alvarohdezarroyo.lookmomicanfly.Models.PhoneNumber;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.SavePhoneNumberRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberMapper {

    @Autowired
    private final UserValidator userValidator;

    public PhoneNumberMapper(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    public PhoneNumber toPhoneNumber(SavePhoneNumberRequestDTO phoneNumberDTO){
        final PhoneNumber phoneNumber=new PhoneNumber();
        phoneNumber.setPrefix(phoneNumberDTO.getPrefix());
        phoneNumber.setNumber(phoneNumberDTO.getDigits());
        phoneNumber.setUser(
                userValidator.returnUserById(phoneNumberDTO.getUserId())
        );
        return phoneNumber;
    }

    public static PhoneNumberDTO toDTO(PhoneNumber phoneNumber){
        if(phoneNumber==null)
            return null;
        final PhoneNumberDTO phoneNumberDTO=new PhoneNumberDTO();
        phoneNumberDTO.setId(phoneNumber.getId());
        phoneNumberDTO.setPrefix(phoneNumber.getPrefix());
        phoneNumberDTO.setNumber(phoneNumber.getNumber());
        return phoneNumberDTO;
    }

    public static PhoneNumberFormatDTO toFormatDTO(PhoneFormat format){
        return new PhoneNumberFormatDTO(format.getPhonePrefix(),format.getNumberLength());
    }

}
