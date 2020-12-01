package com.applaudo.studios.moviestore.repository;

import com.applaudo.studios.moviestore.entity.UserMovieLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserMovieLikeRepo extends JpaRepository<UserMovieLike, Integer>
{
}
