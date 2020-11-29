package com.applaudo.studios.moviestore.service;

import com.applaudo.studios.moviestore.dto.MovieDto;

import java.util.List;

public interface IMovieService
{
    public List<MovieDto> getAll();

    public MovieDto getById(Integer idMovie);

    public Integer save(MovieDto body);

    public MovieDto update(MovieDto body, Integer idMovie);

    public Boolean delete(Integer idMovie);

}
