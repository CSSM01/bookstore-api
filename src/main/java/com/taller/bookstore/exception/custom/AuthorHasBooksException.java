package com.taller.bookstore.exception.custom;

public class AuthorHasBooksException extends RuntimeException {
    public AuthorHasBooksException(Long authorId) {
        super("El autor con id " + authorId + " tiene libros asociados y no puede eliminarse");
    }
}