package com.taller.bookstore.exception.custom;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException() {
        super("No tiene permisos para acceder a este recurso");
    }
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}