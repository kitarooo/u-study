package com.backend.ustudy.exception;

public class IncorrectPasswordsException extends RuntimeException{
    public IncorrectPasswordsException(String message) {
        super(message);
    }
}