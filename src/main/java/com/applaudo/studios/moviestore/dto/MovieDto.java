package com.applaudo.studios.moviestore.dto;

import com.applaudo.studios.moviestore.entity.MovieXPictures;
import com.applaudo.studios.moviestore.entity.UserRent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MovieDto
{
    private Integer id;

    @NotBlank(message = "Title is necessary")
    @Size(min = 1, max = 200)
    private String title;

    @Size(min = 0, max = 500)
    private String description;

    @Min(value = 1)
    private Integer stock;

    @Min(value = 1)
    private Double rentalPrice;

    @Min(value = 1)
    private Double salePrice;

    private Boolean availability;
    private Collection<MovieXPicturesDto> movieXPicturesById;

    @JsonIgnore
    private Collection<UserRent> userRentsById;
}
