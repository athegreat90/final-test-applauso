package com.applaudo.studios.moviestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OrderMovieDetailRentDto
{
    @NotNull(message = "The movie is important")
    private MovieDto movie;


    private LocalDateTime dateBegin;

    @NotNull(message = "The date of return a movie is important")
    @Future(message = "Needs to be before today")
    private LocalDateTime dateEnd;
}
