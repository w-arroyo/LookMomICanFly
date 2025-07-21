package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FailedRequestDTO {

    private String error;

    public FailedRequestDTO(List<String> fields){
        error="";
        fields.forEach(field ->{
            error+=field+",";
        });
    }

}
