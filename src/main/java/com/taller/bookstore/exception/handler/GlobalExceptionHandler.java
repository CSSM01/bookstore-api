package com.taller.bookstore.exception.handler;

import com.taller.bookstore.dto.response.ApiErrorResponse;
import com.taller.bookstore.exception.custom.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return ApiErrorResponse.of(404, ex.getMessage(), List.of(ex.getMessage()), req.getRequestURI());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleDuplicate(DuplicateResourceException ex, HttpServletRequest req) {
        return ApiErrorResponse.of(409, ex.getMessage(), List.of(ex.getMessage()), req.getRequestURI());
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiErrorResponse handleStock(InsufficientStockException ex, HttpServletRequest req) {
        return ApiErrorResponse.of(422, ex.getMessage(), List.of(ex.getMessage()), req.getRequestURI());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleUnauthorized(UnauthorizedAccessException ex, HttpServletRequest req) {
        return ApiErrorResponse.of(403, ex.getMessage(), List.of(ex.getMessage()), req.getRequestURI());
    }

    @ExceptionHandler(InvalidOrderStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleInvalidOrderState(InvalidOrderStateException ex, HttpServletRequest req) {
        return ApiErrorResponse.of(409, ex.getMessage(), List.of(ex.getMessage()), req.getRequestURI());
    }

    @ExceptionHandler(AuthorHasBooksException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleAuthorHasBooks(AuthorHasBooksException ex, HttpServletRequest req) {
        return ApiErrorResponse.of(409, ex.getMessage(), List.of(ex.getMessage()), req.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return ApiErrorResponse.of(400, "Error de validación", errors, req.getRequestURI());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleAccessDenied(AccessDeniedException ex, HttpServletRequest req) {
        return ApiErrorResponse.of(403, "No tiene permisos para esta operación",
                List.of("No tiene permisos para esta operación"), req.getRequestURI());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleAuthentication(AuthenticationException ex, HttpServletRequest req) {
        return ApiErrorResponse.of(401, "Autenticación requerida",
                List.of("Autenticación requerida"), req.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleGeneric(Exception ex, HttpServletRequest req) {
        return ApiErrorResponse.of(500, "Error interno del servidor",
                List.of(ex.getMessage()), req.getRequestURI());
    }
}