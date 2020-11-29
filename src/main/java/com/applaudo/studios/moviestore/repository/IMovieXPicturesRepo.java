package com.applaudo.studios.moviestore.repository;

import com.applaudo.studios.moviestore.entity.MovieXPictures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieXPicturesRepo extends JpaRepository<MovieXPictures, Integer>
{
}
