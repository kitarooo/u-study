package com.backend.ustudy.exception.handler;

import com.backend.ustudy.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExcHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public ExceptionResponse userAlreadyExistException(UserAlreadyExistException e) {
        return new ExceptionResponse(HttpStatus.FOUND, e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFoundException(NotFoundException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND, e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(IncorrectDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse incorrectDataException(IncorrectDataException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(IncorrectPasswordsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse incorrectPasswordException(IncorrectPasswordsException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(IncorrectCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse incorrectCodeException(IncorrectCodeException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(EmptyFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse emptyFileException(EmptyFileException e) {
        return new ExceptionResponse(HttpStatus.NOT_MODIFIED, e.getClass().getName(), e.getMessage());
    }

}
