package com.taller.bookstore.service;

import com.taller.bookstore.dto.request.BookRequest;
import com.taller.bookstore.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponse create(BookRequest request);
    BookResponse getById(Long id);
    Page<BookResponse> getAll(Pageable pageable);
    BookResponse update(Long id, BookRequest request);
    void delete(Long id);
}