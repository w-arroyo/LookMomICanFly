package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.ShippingOption;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ShippingOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingOptionService {

    @Autowired
    private final ShippingOptionRepository shippingOptionRepository;

    public ShippingOptionService(ShippingOptionRepository shippingOptionRepository) {
        this.shippingOptionRepository = shippingOptionRepository;
    }

    public ShippingOption getShippingOptionById(String id){
        return shippingOptionRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Shipping Option Id does not exist.")
        );
    }

    public List<ShippingOption> getAllShippingOptions(){
        return shippingOptionRepository.findAll();
    }

}
