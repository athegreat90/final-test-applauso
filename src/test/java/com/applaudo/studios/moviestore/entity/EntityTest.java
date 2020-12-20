package com.applaudo.studios.moviestore.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EntityTest
{
    @Test
    void movie()
    {
        var dto = new Movie();
        dto.setTitle("DEMO");
        dto.setAvailability(Boolean.TRUE);
        dto.setDescription("DEMO");
        dto.setRentalPrice(1.2D);
        dto.setSalePrice(20D);
        dto.setStock(1);
        assertNotNull(dto);
    }

    @Test
    void movieAllArgs()
    {
        var dto = new Movie(1, "DEMO", "DEMO", 1, 1.2D, 20D, Boolean.TRUE, List.of());
        assertNotNull(dto);
    }

    @Test
    void movieId()
    {
        var dto = new Movie();
        dto.setId(10);
        assertNotNull(dto.getId());
    }

    @Test
    void movieTitle()
    {
        var dto = new Movie();
        dto.setTitle("DEMO");
        assertNotNull(dto.getTitle());
    }

    @Test
    void movieAvailability()
    {
        var dto = new Movie();
        dto.setAvailability(Boolean.TRUE);
        assertNotNull(dto.getAvailability());
    }

    @Test
    void movieDescription()
    {
        var dto = new Movie();
        dto.setDescription("DEMO");
        assertNotNull(dto.getDescription());
    }

    @Test
    void movieRentalPrice()
    {
        var dto = new Movie();
        dto.setRentalPrice(1.2D);
        assertNotNull(dto.getRentalPrice());
    }

    @Test
    void movieUser()
    {
        var dto = new Movie();
        dto.setUserRentsById(List.of());
        assertNotNull(dto.getUserRentsById());
    }

    @Test
    void movieStock()
    {
        var dto = new Movie();
        dto.setStock(1);
        assertNotNull(dto.getStock());
    }

    @Test
    void movieSalesPrice()
    {
        var dto = new Movie();
        dto.setSalePrice(20D);
        assertNotNull(dto.getSalePrice());
    }

    @Test
    void movieToString()
    {
        var dto = new Movie();
        dto.setSalePrice(20D);
        assertNotNull(dto.toString());
    }

    @Test
    void moviePictureAllArgs()
    {
        var entity = new MoviePicture(1, "", List.of());
        assertNotNull(entity.toString());
    }

    @Test
    void moviePictureId()
    {
        var entity = new MoviePicture();
        entity.setId(1);
        assertNotNull(entity.getId());
    }

    @Test
    void moviePictureUrl()
    {
        var entity = new MoviePicture();
        entity.setUrl("");
        assertNotNull(entity.getUrl());
    }

    @Test
    void moviePictureCollection()
    {
        var entity = new MoviePicture();
        entity.setMovieXPicturesById(List.of());
        assertNotNull(entity.getMovieXPicturesById());
    }

    @Test
    void movieXPictureAllArgs()
    {
        var entity = new MovieXPictures(1, new Movie(), new MoviePicture());
        assertNotNull(entity.toString());
    }

    @Test
    void movieXPictureId()
    {
        var entity = new MovieXPictures();
        entity.setId(1);
        assertNotNull(entity.getId());
    }

    @Test
    void movieXPictureMovie()
    {
        var entity = new MovieXPictures();
        entity.setMovieByIdMovie(new Movie());
        assertNotNull(entity.getMovieByIdMovie());
    }

    @Test
    void movieXPictureIdPicture()
    {
        var entity = new MovieXPictures();
        entity.setMoviePictureByIdPicture(new MoviePicture());
        assertNotNull(entity.getMoviePictureByIdPicture());
    }

    @Test
    void roleNoArgs()
    {
        var dto = new Role();
        assertNotNull(dto);
    }

    @Test
    void roleAllArgs()
    {
        var dto = new Role(1, "ADMIN", "ADMIN");
        assertNotNull(dto);
    }

    @Test
    void roleId()
    {
        var dto = new Role();
        dto.setId(1);
        assertNotNull(dto.getId());
    }

    @Test
    void roleName()
    {
        var dto = new Role();
        dto.setName("DEMO");
        assertNotNull(dto.getName());
    }

    @Test
    void roleDescription()
    {
        var dto = new Role();
        dto.setDescription("DEMO");
        assertNotNull(dto.getDescription());
    }

    @Test
    void roleToString()
    {
        var dto = new Role();
        dto.setId(1);
        assertNotNull(dto.toString());
    }

    @Test
    void userMovieLikeAllArgs()
    {
        var entity = new UserMovieLike(1, new UserSystem(), new Movie());
        assertNotNull(entity.toString());
    }

    @Test
    void userMovieLikeId()
    {
        var entity = new UserMovieLike();
        entity.setId(1);
        assertNotNull(entity.getId());
    }

    @Test
    void userMovieLikeMovie()
    {
        var entity = new UserMovieLike();
        entity.setMovieByIdMovie(new Movie());
        assertNotNull(entity.getMovieByIdMovie());
    }

    @Test
    void userMovieLikeUser()
    {
        var entity = new UserMovieLike();
        entity.setUserSystemByUsername(new UserSystem());
        assertNotNull(entity.getUserSystemByUsername());
    }

    @Test
    void userOrderAllArgs()
    {
        var entity = new UserOrder(1, 1.0D, new UserSystem(), new Movie());
        assertNotNull(entity.toString());
    }

    @Test
    void userOrderId()
    {
        var entity = new UserOrder();
        entity.setId(1);
        assertNotNull(entity.getId());
    }

    @Test
    void userOrderCount()
    {
        var entity = new UserOrder();
        entity.setCount(1.0D);
        assertNotNull(entity.getCount());
    }

    @Test
    void userOrderUser()
    {
        var entity = new UserOrder();
        entity.setUserSystemByUsername(new UserSystem());
        assertNotNull(entity.getUserSystemByUsername());
    }

    @Test
    void userOrderMovie()
    {
        var entity = new UserOrder();
        entity.setMovieByIdMovie(new Movie());
        assertNotNull(entity.getMovieByIdMovie());
    }

    @Test
    void userRentAllArgs()
    {
        var entity = new UserRent(1, new Date(), new Date(), new UserSystem(), new Movie());
        assertNotNull(entity.toString());
    }

    @Test
    void userRentId()
    {
        var entity = new UserRent();
        entity.setId(1);
        assertNotNull(entity.getId());
    }

    @Test
    void userRentBegin()
    {
        var entity = new UserRent();
        entity.setDateBegin(new Date());
        assertNotNull(entity.getDateBegin());
    }

    @Test
    void userRentEnd()
    {
        var entity = new UserRent();
        entity.setDateEnd(new Date());
        assertNotNull(entity.getDateEnd());
    }

    @Test
    void userRentUser()
    {
        var entity = new UserRent();
        entity.setUserSystemByUsername(new UserSystem());
        assertNotNull(entity.getUserSystemByUsername());
    }

    @Test
    void userRentMovie()
    {
        var entity = new UserRent();
        entity.setMovieByIdMovie(new Movie());
        assertNotNull(entity.getMovieByIdMovie());
    }

    @Test
    void userRoleAllArgs()
    {
        var entity = new UserRoles("DEMO", 1, new UserSystem(), new Role());
        assertNotNull(entity);
    }

    @Test
    void userRolePkUser()
    {
        var entity = new UserRolesPK();
        entity.setUserId("DEMO");
        assertNotNull(entity.getUserId());
    }

    @Test
    void userRolePkRole()
    {
        var entity = new UserRolesPK();
        entity.setRoleId(1);
        assertNotNull(entity.getRoleId());
    }

    @Test
    void userSystemAllArgs()
    {
        var entity = new UserSystem("DEMO", "DEMO!@#", "DEMO@GMAIL.COM", "DEMO", List.of(), List.of(), new HashSet<>());
        assertNotNull(entity);
    }
}