package com.applaudo.studios.moviestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MoviePictureDto
{
    private Integer id;
    private String url;
}
