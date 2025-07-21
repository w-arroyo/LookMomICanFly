package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Football;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.FootballRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FootballService {

    @Autowired
    private final FootballRepository footballRepository;

    public FootballService(FootballRepository footballRepository) {
        this.footballRepository = footballRepository;
    }

    public Football getFootballById(String id){
        return footballRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Collectible id does not exist.")
        );
    }

    @Transactional
    public Football saveFootball(Football football){
        return footballRepository.save(football);
    }

}
