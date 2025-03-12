package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.CollectibleDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Collectible;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.CollectibleRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectibleService {

    @Autowired
    private final CollectibleRepository collectibleRepository;

    public CollectibleService(CollectibleRepository collectibleRepository) {
        this.collectibleRepository = collectibleRepository;
    }

    public CollectibleDTO getCollectibleDTOById(String id){
        return ProductMapper.toCollectibleDTO(collectibleRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Collectible id does not exist.")
        ));
    }

    @Transactional
    public Collectible saveCollectible(Collectible collectible){
        return collectibleRepository.save(collectible);
    }

}
