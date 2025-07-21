package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.AskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AskService {

    @Autowired
    private final AskRepository askRepository;

    public AskService(AskRepository askRepository) {
        this.askRepository = askRepository;
    }

    @Transactional
    public Ask saveAsk(Ask ask){
        return askRepository.save(ask);
    }

    public List<Ask> getAllUserAsks(String userId){
        return askRepository.findAllUserAsks(userId);
    }

    public Ask findAskById(String id){
        return askRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Ask ID does not exist.")
        );
    }

    public Ask getLowestAsk(String productId, Size size){
        return askRepository.getLowestAskForASizeOfAProduct(productId, size).orElse(null);
    }

    public Integer getLowestAskAmount(String productId, Size size){
        return askRepository.getLowestAskAmount(productId,size.name())
                .orElse(null);
    }

    public Ask getLowestAskNoMatterTheSize(String productId){
        return askRepository.getLowestAskOfAProduct(productId)
                .orElse(null);
    }

}
