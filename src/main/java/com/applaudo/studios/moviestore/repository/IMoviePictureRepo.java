package com.applaudo.studios.moviestore.repository;

import com.applaudo.studios.moviestore.entity.MoviePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMoviePictureRepo extends JpaRepository<MoviePicture, Integer>
{
}
