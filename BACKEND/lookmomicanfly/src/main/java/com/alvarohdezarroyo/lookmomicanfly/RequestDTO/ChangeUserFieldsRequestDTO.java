package com.alvarohdezarroyo.lookmomicanfly.RequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeUserFieldsRequestDTO {
    private String userId, newField;
}
