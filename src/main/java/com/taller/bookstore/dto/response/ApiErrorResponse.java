package com.taller.bookstore.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ApiErrorResponse {

    private String status;
    private int code;
    private String message;
    private List<String> errors;
    private Instant timestamp;
    private String path;

    public static ApiErrorResponse of(int code, String message, List<String> errors, String path) {
        return ApiErrorResponse.builder()
                .status("error")
                .code(code)
                .message(message)
                .errors(errors)
                .timestamp(Instant.now())
                .path(path)
                .build();
    }
}