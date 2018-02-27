package com.king.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "EmployeeException")
public class EmployeeException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public EmployeeException() {
    }

    public EmployeeException(String message) {
        this.message = message;
    }
}
