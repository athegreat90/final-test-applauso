package com.applaudo.studios.moviestore.repository;

import com.applaudo.studios.moviestore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepo extends JpaRepository<Role, Integer>
{
    @Query(value = "SELECT DISTINCT * from role WHERE name = ?1 LIMIT 1", nativeQuery = true)
    Role findRoleByName(String name);
}
