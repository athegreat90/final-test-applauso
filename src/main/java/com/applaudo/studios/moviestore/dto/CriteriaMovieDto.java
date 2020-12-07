package com.applaudo.studios.moviestore.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CriteriaMovieDto
{
    private String name;
    private Boolean availability;
    private Double minPrice;
    private Double maxPrice;
    private Boolean liked;
}
