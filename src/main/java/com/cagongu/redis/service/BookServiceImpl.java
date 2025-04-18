package com.cagongu.redis.service;

import com.cagongu.redis.dto.BookDto;
import com.cagongu.redis.entity.Book;
import com.cagongu.redis.mapper.BookMapper;
import com.cagongu.redis.repository.BookRepository;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private BookMapper bookMapper;
    private final CacheManager cacheManager;
    private static final String BOOK_CACHE = "books";

    @Override
    @CachePut(value = BOOK_CACHE, key = "#result.id")
    public BookDto createBook(BookDto bookDto) {
        clearCache();
        return bookMapper.BookToBookDto(bookRepository.save(bookMapper.BookDtoToBook(bookDto)));
    }

    @Override
    @Cacheable(value = BOOK_CACHE, key = "#id")
    public BookDto getById(Long id) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(() -> new Exception("BOOK NOT FOUND"));

        return bookMapper.BookToBookDto(book);
    }

    @Override
    @Cacheable(value = "bookList", key = "'allBooks'")
    public List<BookDto> getAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(bookMapper::BookToBookDto).toList();
    }

    @Override
    @CacheEvict(value = BOOK_CACHE, key = "#id")
    @CachePut(value = BOOK_CACHE, key = "#result.id")
    public BookDto updateBook(BookDto bookDto, Long id) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(() -> new Exception("BOOK NOT FOUND"));

        if (!bookDto.getDescription().equals(book.getDescription())) {
            book.setDescription(bookDto.getDescription());
        }

        if (!bookDto.getPrice().equals(book.getPrice())) {
            book.setPrice(bookDto.getPrice());
        }

        if (!bookDto.getTitle().equals(book.getTitle())) {
            book.setTitle(bookDto.getTitle());
        }

        bookRepository.save(book);
        clearCache();

        return bookMapper.BookToBookDto(book);
    }

    @Override
    @CacheEvict(value = BOOK_CACHE, key = "#id")
    public void deleteBook(Long id) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(() -> new Exception("BOOK NOT FOUND"));
        bookRepository.delete(book);
        clearCache();
    }

    private void clearCache() {
        if (cacheManager.getCache("bookList") != null) {
            cacheManager.getCache("bookList").evict("allBooks");
        }
    }
}
