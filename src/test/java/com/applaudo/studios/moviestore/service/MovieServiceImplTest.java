package com.applaudo.studios.moviestore.service;

import com.applaudo.studios.moviestore.config.TokenProvider;
import com.applaudo.studios.moviestore.dto.CriteriaMovieDto;
import com.applaudo.studios.moviestore.dto.MovieDto;
import com.applaudo.studios.moviestore.service.rest.IMovieService;
import javassist.NotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieServiceImplTest
{
    @Autowired
    private IMovieService movieService;

    @MockBean
    TokenProvider jwtTokenUtil;

    @Test
    @Order(1)
    void getAll()
    {
        var movies = this.movieService.getAll();
        assertNotNull(movies);
    }

    @Test
    @Order(2)
    void getByCriteria()
    {
        var criteria = new CriteriaMovieDto();
        criteria.setMinPrice(15.0D);
        criteria.setMaxPrice(100.0D);
        criteria.setAvailability(Boolean.TRUE);
        criteria.setLiked(Boolean.TRUE);
        var token  = "Bearer 123";
        when(jwtTokenUtil.getUsernameFromToken(Mockito.anyString())).thenReturn("athegreat90");
        var movies = this.movieService.getByCriteria(criteria, Mockito.anyString());
        assertNotNull(movies);
    }

    @Test
    @Order(2)
    void getByCriteria2()
    {
        var criteria = new CriteriaMovieDto();
        criteria.setMinPrice(15.0D);
        criteria.setMaxPrice(100.0D);
        criteria.setAvailability(null);
        criteria.setLiked(Boolean.TRUE);
        var token  = "Bearer 123";
        when(jwtTokenUtil.getUsernameFromToken(Mockito.anyString())).thenReturn("athegreat90");
        var movies = this.movieService.getByCriteria(criteria, Mockito.anyString());
        assertNotNull(movies);
    }


    @Test
    @Order(3)
    void getById() throws NotFoundException
    {
        var movie = this.movieService.getById(1);
        assertNotNull(movie);
    }

    @Test
    @Order(4)
    void getByIdNotFound()
    {
        assertThrows(NotFoundException.class, () -> this.movieService.getById(10000));
    }

    @Test
    @Order(5)
    void save()
    {
        var dto = new MovieDto();
        dto.setId(10);
        dto.setTitle("DEMO");
        dto.setAvailability(Boolean.TRUE);
//        dto.setMovieXPicturesById(List.of());
        dto.setDescription("DEMO");
        dto.setRentalPrice(1.2D);
        dto.setSalePrice(20D);
        dto.setStock(1);
        assertNotNull(this.movieService.save(dto));
    }

    @Test
    @Order(6)
    void update() throws NotFoundException
    {
        var criteria = new CriteriaMovieDto();
        criteria.setName("DEMO");
        when(jwtTokenUtil.getUsernameFromToken(Mockito.anyString())).thenReturn("athegreat90");
        var movies = this.movieService.getByCriteria(criteria, Mockito.anyString());
        var dto = movies.get(0);
        dto.setDescription("DEMO 2");
        assertNotNull(this.movieService.update(dto, dto.getId()));
    }

    @Test
    @Order(7)
    void updateNotFound() throws NotFoundException
    {
        assertThrows(NotFoundException.class, () -> this.movieService.update(new MovieDto(), 10000));
    }

    @Test
    @Order(8)
    void delete() throws NotFoundException
    {
        var criteria = new CriteriaMovieDto();
        criteria.setName("DEMO");
        when(jwtTokenUtil.getUsernameFromToken(Mockito.anyString())).thenReturn("athegreat90");
        var movies = this.movieService.getByCriteria(criteria, Mockito.anyString());
        var dto = movies.get(0);
        assertNotNull(this.movieService.delete(dto.getId()));
    }

    @Test
    @Order(9)
    void deleteNotFound() throws NotFoundException
    {
        assertThrows(NotFoundException.class, () -> this.movieService.delete(10000));
    }
}