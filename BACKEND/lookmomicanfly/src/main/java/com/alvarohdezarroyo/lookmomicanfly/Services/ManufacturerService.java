package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Manufacturer;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ManufacturerRepository;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public Manufacturer getManufacturerByName(String name){
        return manufacturerRepository.findByName(name).orElseThrow(
                ()->new EntityNotFoundException("Manufacturer name does not exist.")
        );
    }

}
