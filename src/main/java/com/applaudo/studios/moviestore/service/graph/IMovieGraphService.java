package com.applaudo.studios.moviestore.service.graph;

import com.applaudo.studios.moviestore.dto.MovieDto;
import graphql.schema.DataFetcher;
import javassist.NotFoundException;

import java.util.List;

public interface IMovieGraphService
{
    public DataFetcher getAll();

    public DataFetcher getById() throws NotFoundException;
}
