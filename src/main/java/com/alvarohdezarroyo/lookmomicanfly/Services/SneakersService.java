package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SneakersDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Sneakers;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.SneakersRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
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

    public SneakersDTO getSneakersDTOById(String id){
        try {
            return ProductMapper.toSneakersDTO(sneakersRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Sneakers id not found")));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
