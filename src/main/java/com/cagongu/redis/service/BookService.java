package com.cagongu.redis.service;

import com.cagongu.redis.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto);
    BookDto getById(Long id) throws Exception;
    List<BookDto> getAll();
    BookDto updateBook(BookDto bookDto, Long id) throws Exception;
    void deleteBook(Long id) throws Exception;
}
