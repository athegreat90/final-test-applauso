package com.applaudo.studios.moviestore.service;

import com.applaudo.studios.moviestore.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreServiceImplTest
{
    @Autowired
    IStoreService iStoreService;

    @Test
    void buy()
    {
        var user = new UserSystemDto();
        user.setUsername("demo3");
        var movie = new MovieDto();
        movie.setId(1);
        var detail = new OrderMovieDetailDto();
        detail.setCount(1);
        detail.setMovie(movie);
        var req = new UserRequestOrderDto(user, List.of(detail));
        assertNotNull(this.iStoreService.buy(req));
    }

    @Test
    void rent()
    {
        var user = new UserSystemDto();
        user.setUsername("demo3");
        var movie = new MovieDto();
        movie.setId(1);
        var detail = new OrderMovieDetailRentDto();
        detail.setDateBegin(LocalDateTime.now());
        detail.setDateEnd(LocalDateTime.of(2020, 12, 13, 15, 16));
        detail.setMovie(movie);
        var req = new UserRequestRentOrderDto(user, List.of(detail));
        assertNotNull(this.iStoreService.rent(req));
    }

    @Test
    void like()
    {
        var user = new UserSystemDto();
        user.setUsername("demo3");
        var movie = new MovieDto();
        movie.setId(1);
        var req = new UserLikeDto(user, List.of(movie));
        assertNotNull(this.iStoreService.like(req));
    }
}