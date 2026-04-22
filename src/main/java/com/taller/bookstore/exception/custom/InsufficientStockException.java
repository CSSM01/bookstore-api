package com.taller.bookstore.exception.custom;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String title, int available) {
        super("Stock insuficiente para '" + title + "'. Disponible: " + available);
    }
}