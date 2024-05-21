package ru.kpoison.restdemo.util;

import org.springframework.http.HttpStatus;

public abstract class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatus();
}
