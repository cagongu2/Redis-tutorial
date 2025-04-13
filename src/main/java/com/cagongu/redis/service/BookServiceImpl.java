package com.cagongu.redis.service;

import com.cagongu.redis.dto.BookDto;
import com.cagongu.redis.entity.Book;
import com.cagongu.redis.mapper.BookMapper;
import com.cagongu.redis.repository.BookRepository;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private BookMapper bookMapper;

    @Override
    public BookDto createBook(BookDto bookDto) {
        return bookMapper.BookToBookDto(bookRepository.save(bookMapper.BookDtoToBook(bookDto)));
    }

    @Override
    public BookDto getById(Long id) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(() -> new Exception("BOOK NOT FOUND"));

        return bookMapper.BookToBookDto(book);
    }

    @Override
    public List<BookDto> getAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(bookMapper::BookToBookDto).toList();
    }

    @Override
    public BookDto updateBook(BookDto bookDto, Long id) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(() -> new Exception("BOOK NOT FOUND"));

        if(!bookDto.getDescription().equals(book.getDescription())){
            book.setDescription(bookDto.getDescription());
        }

        if(!bookDto.getPrice().equals( book.getPrice())){
            book.setPrice(bookDto.getPrice());
        }

        if(!bookDto.getTitle().equals(book.getTitle()) ){
            book.setTitle(bookDto.getTitle());
        }

        bookRepository.save(book);

        return bookMapper.BookToBookDto(book);
    }

    @Override
    public void deleteBook(Long id) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(() -> new Exception("BOOK NOT FOUND"));
        bookRepository.delete(book);
    }
}
