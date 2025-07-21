package com.alvarohdezarroyo.lookmomicanfly.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {

    private String userId, productId, size, addressId;
    private Integer amount;

}
