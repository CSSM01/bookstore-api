package com.taller.bookstore.controller;

import com.taller.bookstore.dto.request.AuthorRequest;
import com.taller.bookstore.dto.response.ApiResponse;
import com.taller.bookstore.dto.response.AuthorResponse;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorResponse>> create(@Valid @RequestBody AuthorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(authorService.create(request), "Autor creado exitosamente", 201));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(authorService.getById(id), "Autor encontrado"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(authorService.getAll(), "Lista de autores"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponse>> update(@PathVariable Long id,
                                                              @Valid @RequestBody AuthorRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authorService.update(id, request), "Autor actualizado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Autor eliminado exitosamente"));
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<ApiResponse<Page<BookResponse>>> getBooks(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(authorService.getBooksByAuthor(id, pageable), "Libros del autor"));
    }
}