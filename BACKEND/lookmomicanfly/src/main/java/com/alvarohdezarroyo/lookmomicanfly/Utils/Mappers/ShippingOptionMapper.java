package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ShippingOptionDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.ShippingOption;
import org.mapstruct.Mapper;

@Mapper
public class ShippingOptionMapper {

    public static ShippingOptionDTO toDTO(ShippingOption shippingOption){
        final ShippingOptionDTO shippingOptionDTO=new ShippingOptionDTO();
        shippingOptionDTO.setId(shippingOption.getId());
        shippingOptionDTO.setEstimatedTime(shippingOption.getEstimatedTime());
        shippingOptionDTO.setName(shippingOption.getName());
        shippingOptionDTO.setPrice(shippingOption.getPrice());
        shippingOptionDTO.setCompanyName(shippingOption.getShippingCompany().getName());
        return shippingOptionDTO;
    }

}
