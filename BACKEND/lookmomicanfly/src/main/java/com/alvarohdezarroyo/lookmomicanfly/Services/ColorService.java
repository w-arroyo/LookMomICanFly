package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Color;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ColorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorService {

    @Autowired
    private final ColorRepository colorRepository;

    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public Color findColorByName(String colorName){
        return colorRepository.findByName(colorName).orElseThrow(
                ()-> new EntityNotFoundException("Color name not found.")
        );
    }

    @Transactional
    public void saveProductColors(List<Color> colors, String productId){
        colors.forEach(
                color -> colorRepository.insertProductColors(productId,color.getId())
        );
    }

}
