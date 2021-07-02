package com.applaudo.studios.moviestore.service.graph;

import com.applaudo.studios.moviestore.config.TokenProvider;
import com.applaudo.studios.moviestore.dto.MovieDto;
import com.applaudo.studios.moviestore.entity.Movie;
import com.applaudo.studios.moviestore.repository.IMovieRepo;
import com.applaudo.studios.moviestore.repository.IUserRolesRepo;
import graphql.schema.DataFetcher;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieGraphService implements IMovieGraphService
{
    private final IMovieRepo movieRepo;
    private final ModelMapper modelMapper;
    private final TokenProvider jwtTokenUtil;
    private final IUserRolesRepo iUserRolesRepo;

    @Override
    public DataFetcher getAll()
    {
        return dataFetchingEnvironment -> movieRepo.findAll();
    }

    @Override
    public DataFetcher getById() throws NotFoundException {
        return dataFetchingEnvironment ->
        {
            int id = dataFetchingEnvironment.getArgument("id");
            Optional<Movie> original = this.movieRepo.findById(id);
            return this.modelMapper.map(original.get(), MovieDto.class);
        };
    }
}
