package com.applaudo.studios.moviestore.repository;

import com.applaudo.studios.moviestore.entity.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserSystemRepo extends JpaRepository<UserSystem, String>
{
}
