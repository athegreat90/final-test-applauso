package com.applaudo.studios.moviestore.repository;

import com.applaudo.studios.moviestore.entity.UserRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRentRepo extends JpaRepository<UserRent, Integer>
{
}
