package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Accessory;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.AccesoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessoryService {

    @Autowired
    private final AccesoryRepository accesoryRepository;

    public AccessoryService(AccesoryRepository accesoryRepository) {
        this.accesoryRepository = accesoryRepository;
    }

    public Accessory getAccessoryById(String id){
        return accesoryRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Accessory id does not exist.")
        );
    }

    @Transactional
    public Accessory saveAccessory(Accessory accessory){
        return accesoryRepository.save(accessory);
    }

}
