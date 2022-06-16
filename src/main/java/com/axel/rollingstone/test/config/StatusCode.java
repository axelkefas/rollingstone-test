package com.axel.rollingstone.test.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum StatusCode {
    OK(HttpStatus.OK,"Success"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Data not Found"),
    DUPLICATE(HttpStatus.BAD_REQUEST, "Data Already Exists"),
    WRONG_CREDENTIAL(HttpStatus.UNAUTHORIZED, "Wrong Credential"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Please Login First");

    private final HttpStatus httpStatus;
    private final String message;
}
