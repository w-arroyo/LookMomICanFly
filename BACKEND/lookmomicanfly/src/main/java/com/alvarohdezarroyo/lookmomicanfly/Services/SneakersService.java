package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Sneakers;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.SneakersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SneakersService {

    @Autowired
    private final SneakersRepository sneakersRepository;

    public SneakersService(SneakersRepository sneakersRepository) {
        this.sneakersRepository = sneakersRepository;
    }

    @Transactional
    public Sneakers saveSneakers(Sneakers sneakers){
        return sneakersRepository.save(sneakers);
    }

    public Sneakers getSneakersById(String id){
        return sneakersRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Sneakers does not exist.")
        );
    }

}
