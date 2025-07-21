package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Electronic;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ElectronicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectronicService {

    @Autowired
    private final ElectronicRepository electronicRepository;

    public ElectronicService(ElectronicRepository electronicRepository) {
        this.electronicRepository = electronicRepository;
    }

    public Electronic getElectronicById(String id){
        return electronicRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Collectible id does not exist.")
        );
    }

    @Transactional
    public Electronic saveElectronic(Electronic electronic){
        return electronicRepository.save(electronic);
    }

}
