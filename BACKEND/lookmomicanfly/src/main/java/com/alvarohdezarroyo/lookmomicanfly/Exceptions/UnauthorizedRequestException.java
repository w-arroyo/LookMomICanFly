package com.alvarohdezarroyo.lookmomicanfly.Exceptions;

public class UnauthorizedRequestException extends RuntimeException {
    public UnauthorizedRequestException(String message) {
        super(message);
    }
}
