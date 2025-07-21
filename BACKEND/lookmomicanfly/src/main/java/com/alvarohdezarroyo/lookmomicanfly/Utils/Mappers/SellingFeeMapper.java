package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SellingFeeDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.SellingFee;
import org.springframework.stereotype.Component;

@Component
public class SellingFeeMapper {

    public static SellingFeeDTO toDTO(SellingFee sellingFee){
        final SellingFeeDTO sellingFeeDTO=new SellingFeeDTO();
        sellingFeeDTO.setId(sellingFee.getId());
        sellingFeeDTO.setDescription(sellingFee.getDescription().replace("_"," "));
        sellingFeeDTO.setPercentage(sellingFee.getPercentage());
        sellingFeeDTO.setByDefault(sellingFee.getByDefault());
        return sellingFeeDTO;
    }

    public static SellingFee toSellingFee(SellingFeeDTO sellingFeeDTO){
        final SellingFee sellingFee=new SellingFee();
        sellingFee.setByDefault(true);
        sellingFee.setDescription(sellingFeeDTO.getDescription());
        sellingFee.setPercentage(sellingFeeDTO.getPercentage());
        sellingFee.setByDefault(sellingFeeDTO.isByDefault());
        return sellingFee;
    }

}
