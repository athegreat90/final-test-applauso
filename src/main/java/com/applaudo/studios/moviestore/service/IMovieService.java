package com.applaudo.studios.moviestore.service;

import com.applaudo.studios.moviestore.dto.MovieDto;
import javassist.NotFoundException;

import java.util.List;

public interface IMovieService
{
    public List<MovieDto> getAll();

    public MovieDto getById(Integer idMovie);

    public Integer save(MovieDto body);

    public MovieDto update(MovieDto body, Integer idMovie) throws NotFoundException;

    public Boolean delete(Integer idMovie) throws NotFoundException;

}
