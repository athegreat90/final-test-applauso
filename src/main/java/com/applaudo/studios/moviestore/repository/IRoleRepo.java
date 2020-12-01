package com.applaudo.studios.moviestore.repository;

import com.applaudo.studios.moviestore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepo extends JpaRepository<Role, Integer>
{
}
