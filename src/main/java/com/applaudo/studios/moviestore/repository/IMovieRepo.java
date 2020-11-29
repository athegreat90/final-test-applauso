package com.applaudo.studios.moviestore.repository;

import com.applaudo.studios.moviestore.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepo extends JpaRepository<Movie, Integer>
{
}
