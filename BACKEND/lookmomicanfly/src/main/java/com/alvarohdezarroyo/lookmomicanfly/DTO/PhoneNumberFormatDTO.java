package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PhoneNumberFormatDTO {

    private String prefix;
    private int digits;

}
