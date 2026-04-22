package com.taller.bookstore.service;

import com.taller.bookstore.dto.request.AuthorRequest;
import com.taller.bookstore.dto.response.AuthorResponse;
import com.taller.bookstore.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {
    AuthorResponse create(AuthorRequest request);
    AuthorResponse getById(Long id);
    List<AuthorResponse> getAll();
    AuthorResponse update(Long id, AuthorRequest request);
    void delete(Long id);
    Page<BookResponse> getBooksByAuthor(Long authorId, Pageable pageable);
}