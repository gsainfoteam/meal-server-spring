package com.example.helper.advice;

import com.example.helper.dto.ErrorDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class NotFoundAdvice {
    @ExceptionHandler(ResponseStatusException.class)
    public ErrorDto handle(ResponseStatusException e) {
        return new ErrorDto(e.getReason());
    }
}
