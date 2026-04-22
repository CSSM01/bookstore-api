package com.taller.bookstore.service.impl;

import com.taller.bookstore.dto.request.BookRequest;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.entity.Book;
import com.taller.bookstore.entity.Category;
import com.taller.bookstore.exception.custom.DuplicateResourceException;
import com.taller.bookstore.exception.custom.ResourceNotFoundException;
import com.taller.bookstore.mapper.BookMapper;
import com.taller.bookstore.repository.AuthorRepository;
import com.taller.bookstore.repository.BookRepository;
import com.taller.bookstore.repository.CategoryRepository;
import com.taller.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    public BookResponse create(BookRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateResourceException("Ya existe un libro con ISBN: " + request.getIsbn());
        }
        Book book = bookMapper.toEntity(request);
        book.setAuthor(authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author", request.getAuthorId())));
        book.setCategories(resolveCategories(request.getCategoryIds()));
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    public BookResponse getById(Long id) {
        return bookMapper.toResponse(findById(id));
    }

    @Override
    public Page<BookResponse> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toResponse);
    }

    @Override
    public BookResponse update(Long id, BookRequest request) {
        Book book = findById(id);
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setStock(request.getStock());
        book.setDescription(request.getDescription());
        book.setAuthor(authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author", request.getAuthorId())));
        book.setCategories(resolveCategories(request.getCategoryIds()));
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) {
        findById(id);
        bookRepository.deleteById(id);
    }

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", id));
    }

    private List<Category> resolveCategories(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();
        return ids.stream()
                .map(cid -> categoryRepository.findById(cid)
                        .orElseThrow(() -> new ResourceNotFoundException("Category", cid)))
                .collect(Collectors.toList());
    }
}