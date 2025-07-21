package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Models.PhoneNumber;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.PhoneNumberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberService {

    @Autowired
    private final PhoneNumberRepository phoneNumberRepository;

    public PhoneNumberService(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    @Transactional
    public void deactivateAllUserPhoneNumbers(String userId){
        phoneNumberRepository.deactivateAllUserPhones(userId);
    }

    @Transactional
    public PhoneNumber savePhoneNumber(PhoneNumber phoneNumber){
        return phoneNumberRepository.save(phoneNumber);
    }

    public PhoneNumber getUserPhoneNumber(String userId){
        return phoneNumberRepository.getUserPhoneNumber(userId)
                .orElse(null);
    }

}
