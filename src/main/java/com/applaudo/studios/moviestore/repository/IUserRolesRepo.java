package com.applaudo.studios.moviestore.repository;

import com.applaudo.studios.moviestore.entity.UserRoles;
import com.applaudo.studios.moviestore.entity.UserRolesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRolesRepo extends JpaRepository<UserRoles, UserRolesPK>
{
    @Query(value = "SELECT DISTINCT * from applauso.user_roles WHERE user_id = ?1", nativeQuery = true)
    List<UserRoles> findUserRolesByUserId(String id);
}
