package com.applaudo.studios.moviestore.service;

import com.applaudo.studios.moviestore.dto.MovieDto;
import com.applaudo.studios.moviestore.entity.Movie;
import com.applaudo.studios.moviestore.repository.IMovieRepo;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements IMovieService
{
    private final IMovieRepo movieRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<MovieDto> getAll()
    {
        List<Movie> movieList = this.movieRepo.findAll();
        Type listType = new TypeToken<List<MovieDto>>() {}.getType();
        List<MovieDto> result = this.modelMapper.map(movieList, listType);
        return result;
    }

    @Override
    public MovieDto getById(Integer idMovie) {
        return null;
    }

    @Override
    public Integer save(MovieDto body)
    {
        Movie movie = this.modelMapper.map(body, Movie.class);
        Movie movieSaved = this.movieRepo.saveAndFlush(movie);
        return movieSaved.getId();
    }

    @Override
    public MovieDto update(MovieDto body, Integer idMovie) throws NotFoundException
    {
        Optional<Movie> original = this.movieRepo.findById(idMovie);
        if (original.isPresent())
        {
            body.setId(idMovie);
            Movie movie = this.modelMapper.map(body, Movie.class);
            Movie movieSaved = this.movieRepo.saveAndFlush(movie);
            return this.modelMapper.map(movieSaved, MovieDto.class);
        }
        else
        {
            throw new NotFoundException("The id " + idMovie + " doesn't exists");
        }
    }

    @Override
    public Boolean delete(Integer idMovie) throws NotFoundException
    {
        Optional<Movie> original = this.movieRepo.findById(idMovie);
        if (original.isPresent())
        {
            this.movieRepo.delete(original.get());
            return Boolean.TRUE;
        }
        else
        {
            throw new NotFoundException("The id " + idMovie + " doesn't exists");
        }
    }
}
