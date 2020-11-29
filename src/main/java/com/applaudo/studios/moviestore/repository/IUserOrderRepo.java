package com.applaudo.studios.moviestore.repository;

import com.applaudo.studios.moviestore.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserOrderRepo extends JpaRepository<UserOrder, Integer>
{
}
