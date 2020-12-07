package com.applaudo.studios.moviestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieXPicturesDto
{
    private Integer id;
    private MovieDto movieByIdMovie;
    private MoviePictureDto moviePictureByIdPicture;
}
