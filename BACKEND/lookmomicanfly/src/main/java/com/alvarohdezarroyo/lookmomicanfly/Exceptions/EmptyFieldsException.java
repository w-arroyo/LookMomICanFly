package com.alvarohdezarroyo.lookmomicanfly.Exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class EmptyFieldsException extends RuntimeException {

    private List<String> emptyFields;

    public EmptyFieldsException(List<String> emptyFields) {
        this.emptyFields=emptyFields;
    }
    public EmptyFieldsException(String message){
        super(message);
    }
}
