package com.applaudo.studios.moviestore.service;

import com.applaudo.studios.moviestore.dto.CriteriaMovieDto;
import com.applaudo.studios.moviestore.dto.MovieDto;
import javassist.NotFoundException;

import java.util.List;

public interface IMovieService
{
    public List<MovieDto> getAll();

    public List<MovieDto> getByCriteria(CriteriaMovieDto criteria, String token);

    public MovieDto getById(Integer idMovie) throws NotFoundException;

    public Integer save(MovieDto body);

    public MovieDto update(MovieDto body, Integer idMovie) throws NotFoundException;

    public Boolean delete(Integer idMovie) throws NotFoundException;

}
