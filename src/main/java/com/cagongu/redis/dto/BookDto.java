package com.cagongu.redis.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    Long id;
    @NotNull
    String title;
    String description;
    @Positive
    Double price;
}
