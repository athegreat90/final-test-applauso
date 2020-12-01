package com.applaudo.studios.moviestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class UserRequestRentOrderDto
{
    @NotNull(message = "The buyer is important")
    private UserSystemDto buyer;

    @NotNull(message = "The list of movies is important")
    private List<OrderMovieDetailRentDto> movieList;
}
