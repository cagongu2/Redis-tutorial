package com.cagongu.redis.controller;

import com.cagongu.redis.dto.BookDto;
import com.cagongu.redis.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {
    private BookService bookService;

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable("id") Long id) throws Exception {
        return  bookService.getById(id);
    }

    @GetMapping
    public List<BookDto> getALl() throws Exception {
        return  bookService.getAll();
    }

    @PostMapping
    public BookDto createBook(@RequestBody @Valid BookDto bookDto){
        return bookService.createBook(bookDto);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@RequestBody @Valid BookDto bookDto, @PathVariable("id") Long id) throws Exception {
        return bookService.updateBook(bookDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") Long id) throws Exception {
        bookService.deleteBook(id);
    }


}
