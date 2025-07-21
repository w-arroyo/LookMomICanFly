package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Clothing;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ClothingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClothingService {

    @Autowired
    private final ClothingRepository clothingRepository;

    public ClothingService(ClothingRepository clothingRepository) {
        this.clothingRepository = clothingRepository;
    }

    public Clothing getClothingById(String id){
        return clothingRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Clothing id does not exist.")
        );
    }

    @Transactional
    public Clothing saveClothing(Clothing clothing){
        return clothingRepository.save(clothing);
    }

}
