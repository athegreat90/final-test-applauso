package com.applaudo.studios.moviestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OrderMovieDetailDto
{
    @NotNull(message = "The movie is important")
    private MovieDto movie;

    @NotNull(message = "The count of movie is important")
    private Integer count;
}
