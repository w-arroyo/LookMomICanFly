package com.alvarohdezarroyo.lookmomicanfly.RequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavePhoneNumberRequestDTO {

    private String prefix, userId;
    private String digits;

}
