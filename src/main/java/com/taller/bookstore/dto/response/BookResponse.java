package com.taller.bookstore.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private String isbn;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private AuthorResponse author;
    private List<CategoryResponse> categories;
}