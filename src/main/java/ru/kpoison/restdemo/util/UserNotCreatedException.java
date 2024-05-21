package ru.kpoison.restdemo.util;

import org.springframework.http.HttpStatus;

public class UserNotCreatedException extends UserException {
    public UserNotCreatedException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
