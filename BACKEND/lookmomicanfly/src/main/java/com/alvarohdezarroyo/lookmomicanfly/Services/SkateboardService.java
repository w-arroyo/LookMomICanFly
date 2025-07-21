package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Skateboard;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.SkateboardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkateboardService {

    @Autowired
    private final SkateboardRepository skateboardRepository;

    public SkateboardService(SkateboardRepository skateboardRepository) {
        this.skateboardRepository = skateboardRepository;
    }

    public Skateboard findSkateboardById(String id){
        return skateboardRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Skateboard id does not exist.")
        );
    }

    @Transactional
    public Skateboard saveSkateboard(Skateboard skateboard){
        return skateboardRepository.save(skateboard);
    }

}
