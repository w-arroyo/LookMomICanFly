package com.alvarohdezarroyo.lookmomicanfly.RequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePostRequestDTO {

    private String postId,userId;
    private Integer amount;

}
