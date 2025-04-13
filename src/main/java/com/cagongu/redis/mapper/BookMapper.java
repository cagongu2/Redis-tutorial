package com.cagongu.redis.mapper;

import com.cagongu.redis.dto.BookDto;
import com.cagongu.redis.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book BookDtoToBook(BookDto bookDto);
    BookDto BookToBookDto(Book book);
}
