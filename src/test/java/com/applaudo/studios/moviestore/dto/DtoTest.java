package com.applaudo.studios.moviestore.dto;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest
{
    @Test
    void criteriaMovieDto()
    {
        var dto = new CriteriaMovieDto();
        dto.setName("DEMO");
        dto.setMinPrice(1.0D);
        dto.setMaxPrice(100D);
        dto.setLiked(Boolean.TRUE);
        dto.setAvailability(Boolean.TRUE);
        assertNotNull(dto);
    }

    @Test
    void criteriaMovieDtoName()
    {
        var dto = new CriteriaMovieDto();
        dto.setName("DEMO");
        assertNotNull(dto.getName());
    }

    @Test
    void criteriaMovieDtoMinPrice()
    {
        var dto = new CriteriaMovieDto();
        dto.setMinPrice(1.0D);
        assertNotNull(dto.getMinPrice());
    }

    @Test
    void criteriaMovieDtoMaxPrice()
    {
        var dto = new CriteriaMovieDto();
        dto.setMaxPrice(100.0D);
        assertNotNull(dto.getMaxPrice());
    }

    @Test
    void criteriaMovieDtoLike()
    {
        var dto = new CriteriaMovieDto();
        dto.setLiked(Boolean.TRUE);
        assertNotNull(dto.getLiked());
    }

    @Test
    void criteriaMovieDtoAvailability()
    {
        var dto = new CriteriaMovieDto();
        dto.setAvailability(Boolean.TRUE);
        assertNotNull(dto.getAvailability());
    }

    @Test
    void criteriaMovieDtoAllArg()
    {
        var dto = new CriteriaMovieDto("DEMO", Boolean.TRUE, 1D, 100D, Boolean.TRUE);
        assertNotNull(dto);
    }

    @Test
    void movieDto()
    {
        var dto = new MovieDto();
        dto.setTitle("DEMO");
        dto.setAvailability(Boolean.TRUE);
        dto.setDescription("DEMO");
        dto.setRentalPrice(1.2D);
        dto.setSalePrice(20D);
        dto.setStock(1);
        assertNotNull(dto);
    }

    @Test
    void movieDtoAllArgs()
    {
        var dto = new MovieDto(1, "DEMO", "DEMO", 1, 1.2D, 20D, Boolean.TRUE, List.of(), List.of());
        assertNotNull(dto);
    }

    @Test
    void movieDtoId()
    {
        var dto = new MovieDto();
        dto.setId(1);
        assertNotNull(dto.getId());
    }

    @Test
    void movieDtoTitle()
    {
        var dto = new MovieDto();
        dto.setTitle("DEMO");
        assertNotNull(dto.getTitle());
    }

    @Test
    void movieDtoAvailability()
    {
        var dto = new MovieDto();
        dto.setAvailability(Boolean.TRUE);
        assertNotNull(dto.getAvailability());
    }

    @Test
    void movieDtoMoviesXPicture()
    {
        var dto = new MovieDto();
        dto.setMovieXPicturesById(List.of());
        assertNotNull(dto.getMovieXPicturesById());
    }

    @Test
    void movieDtoDescription()
    {
        var dto = new MovieDto();
        dto.setDescription("DEMO 2");
        assertNotNull(dto.getDescription());
    }

    @Test
    void movieDtoRentalPrice()
    {
        var dto = new MovieDto();
        dto.setRentalPrice(1.2D);
        assertNotNull(dto.getRentalPrice());
    }

    @Test
    void movieDtoUser()
    {
        var dto = new MovieDto();
        dto.setUserRentsById(List.of());
        assertNotNull(dto.getUserRentsById());
    }

    @Test
    void movieDtoStock()
    {
        var dto = new MovieDto();
        dto.setStock(2);
        assertNotNull(dto.getStock());
    }

    @Test
    void movieDtoSalesPrice()
    {
        var dto = new MovieDto();
        dto.setSalePrice(20D);
        assertNotNull(dto.getSalePrice());
    }

    @Test
    void movieDtoToString()
    {
        var dto = new MovieDto();
        dto.setSalePrice(20D);
        assertNotNull(dto.toString());
    }

    @Test
    void orderMovieDetailDto()
    {
        var dto = new OrderMovieDetailDto();
        dto.setMovie(new MovieDto());
        dto.setCount(1);
        assertNotNull(dto);
    }

    @Test
    void orderMovieDetailDtoAllArgs()
    {
        var dto = new OrderMovieDetailDto(new MovieDto(), 1);
        assertNotNull(dto);
    }

    @Test
    void orderMovieDetailDtoToString()
    {
        var dto = new OrderMovieDetailDto(new MovieDto(), 1);
        assertNotNull(dto.toString());
    }

    @Test
    void orderMovieDetailDtoMovieDto()
    {
        var dto = new OrderMovieDetailDto();
        dto.setMovie(new MovieDto());
        assertNotNull(dto.getMovie());
    }

    @Test
    void orderMovieDetailDtoCount()
    {
        var dto = new OrderMovieDetailDto();
        dto.setCount(1);
        assertNotNull(dto.getCount());
    }

    @Test
    void orderMovieDetailRentDtoNoArgs()
    {
        var dto = new OrderMovieDetailRentDto();
        dto.setMovie(new MovieDto());
        dto.setDateBegin(LocalDateTime.now());
        dto.setDateEnd(LocalDateTime.MAX);
        assertNotNull(dto);
    }

    @Test
    void orderMovieDetailRentDtoAllArgs()
    {
        var dto = new OrderMovieDetailRentDto(new MovieDto(), LocalDateTime.now(), LocalDateTime.now().plusDays(2L));
        assertNotNull(dto);
    }

    @Test
    void orderMovieDetailRentDtoToString()
    {
        var dto = new OrderMovieDetailRentDto(new MovieDto(), LocalDateTime.now(), LocalDateTime.now().plusDays(2L));
        assertNotNull(dto.toString());
    }

    @Test
    void orderMovieDetailDtoMovie()
    {
        var dto = new OrderMovieDetailRentDto();
        dto.setMovie(new MovieDto());
        assertNotNull(dto.getMovie());
    }

    @Test
    void orderMovieDetailDtoDateBegin()
    {
        var dto = new OrderMovieDetailRentDto();
        dto.setDateBegin(LocalDateTime.now());
        assertNotNull(dto.getDateBegin());
    }

    @Test
    void orderMovieDetailDtoDateEnd()
    {
        var dto = new OrderMovieDetailRentDto();
        dto.setDateEnd(LocalDateTime.MAX);
        assertNotNull(dto.getDateEnd());
    }

    @Test
    void roleDtoNoArgs()
    {
        var dto = new RoleDto();
        assertNotNull(dto);
    }

    @Test
    void roleDtoAllArgs()
    {
        var dto = new RoleDto(1, "ADMIN", "ADMIN");
        assertNotNull(dto);
    }

    @Test
    void roleDtoId()
    {
        var dto = new RoleDto();
        dto.setId(1);
        assertNotNull(dto.getId());
    }

    @Test
    void roleDtoName()
    {
        var dto = new RoleDto();
        dto.setName("ADMIN");
        assertNotNull(dto.getName());
    }

    @Test
    void roleDtoDescription()
    {
        var dto = new RoleDto();
        dto.setDescription("DEMO 2");
        assertNotNull(dto.getDescription());
    }

    @Test
    void roleDtoToString()
    {
        var dto = new RoleDto();
        dto.setId(1);
        assertNotNull(dto.toString());
    }

    @Test
    void userLikeDtoNoArgs()
    {
        var dto = new UserLikeDto();
        dto.setUser(new UserSystemDto());
        dto.setListMovies(List.of(new MovieDto()));
        assertNotNull(dto);
    }

    @Test
    void userLikeDtoAllArgs()
    {
        var dto = new UserLikeDto(new UserSystemDto(), List.of());
        assertNotNull(dto);
    }

    @Test
    void userLikeDtoUser()
    {
        var dto = new UserLikeDto();
        dto.setUser(new UserSystemDto());
        assertNotNull(dto.getUser());
    }

    @Test
    void userLikeDtoListMovies()
    {
        var dto = new UserLikeDto();
        dto.setListMovies(List.of());
        assertNotNull(dto.getListMovies());
    }

    @Test
    void userLikeDtoToString()
    {
        var dto = new UserLikeDto();
        dto.setUser(new UserSystemDto());
        dto.setListMovies(List.of());
        assertNotNull(dto.toString());
    }

    @Test
    void userRepoReqDtoNoArgs()
    {
        var dto = new UserRepoReqDto();
        dto.setUserDetail(new UserSystemDto());
        dto.setRoles(List.of());
        assertNotNull(dto);
    }

    @Test
    void userRepoReqDtoAllArgs()
    {
        var dto = new UserRepoReqDto(new UserSystemDto(),List.of());
        assertNotNull(dto);
    }

    @Test
    void userRepoReqDtoUser()
    {
        var dto = new UserRepoReqDto();
        dto.setUserDetail(new UserSystemDto());
        assertNotNull(dto.getUserDetail());
    }

    @Test
    void userRepoReqDtoListRoles()
    {
        var dto = new UserRepoReqDto();
        dto.setRoles(List.of());
        assertNotNull(dto.getRoles());
    }

    @Test
    void userRepoReqDtoToString()
    {
        var dto = new UserRepoReqDto();
        dto.setUserDetail(new UserSystemDto());
        dto.setRoles(List.of(new RoleDto()));
        assertNotNull(dto.toString());
    }

    @Test
    void userRequestOrderDtoNoArgs()
    {
        var dto = new UserRequestOrderDto();
        dto.setBuyer(new UserSystemDto());
        dto.setMovieList(List.of());
        assertNotNull(dto);
    }

    @Test
    void userRequestOrderDtoAllArgs()
    {
        var dto = new UserRequestOrderDto(new UserSystemDto(), List.of());
        assertNotNull(dto);
    }

    @Test
    void userRequestOrderDtoUser()
    {
        var dto = new UserRequestOrderDto();
        dto.setBuyer(new UserSystemDto());
        assertNotNull(dto.getBuyer());
    }

    @Test
    void UserRequestOrderDtoListMovies()
    {
        var dto = new UserRequestOrderDto();
        dto.setMovieList(List.of());
        assertNotNull(dto.getMovieList());
    }

    @Test
    void userRequestOrderDtoToString()
    {
        var dto = new UserRequestOrderDto();
        dto.setBuyer(new UserSystemDto());
        dto.setMovieList(List.of());
        assertNotNull(dto.toString());
    }

    @Test
    void userRequestRentOrderDtoNoArgs()
    {
        var dto = new UserRequestRentOrderDto();
        dto.setBuyer(new UserSystemDto());
        dto.setMovieList(List.of(new OrderMovieDetailRentDto()));
        assertNotNull(dto);
    }

    @Test
    void userRequestRentOrderDtoAllArgs()
    {
        var dto = new UserRequestRentOrderDto(new UserSystemDto(), List.of());
        assertNotNull(dto);
    }

    @Test
    void userRequestRentOrderDtoUser()
    {
        var dto = new UserRequestRentOrderDto();
        dto.setBuyer(new UserSystemDto());
        assertNotNull(dto.getBuyer());
    }

    @Test
    void UserRequestRentOrderDtoListMovies()
    {
        var dto = new UserRequestRentOrderDto();
        dto.setMovieList(List.of());
        assertNotNull(dto.getMovieList());
    }

    @Test
    void userRequestRentOrderDtoToString()
    {
        var dto = new UserRequestRentOrderDto();
        dto.setBuyer(new UserSystemDto());
        dto.setMovieList(List.of());
        assertNotNull(dto.toString());
    }

    @Test
    void userSystemDtoAllArgs()
    {
        var dto = new UserSystemDto("DEMO", "DEMO!@#", "DEMO@GMAIL.COM", "DEMO");
        assertNotNull(dto);
    }

    @Test
    void userSystemShowDtoNoArgs()
    {
        var dto = new UserSystemShowDto();
        dto.setName("DEMO");
        dto.setUsername("DEMO");
        dto.setEmail("DEMO");
        assertNotNull(dto);
    }

    @Test
    void userSystemShowDtoAllArgs()
    {
        var dto = new UserSystemShowDto("DEMO", "DEMO@GMAIL.COM", "DEMO");
        assertNotNull(dto);
    }

    @Test
    void userSystemShowDtoName()
    {
        var dto = new UserSystemShowDto();
        dto.setName("DEMO");
        assertNotNull(dto.getName());
    }

    @Test
    void userSystemShowDtoUsername()
    {
        var dto = new UserSystemShowDto();
        dto.setUsername("DEMO");
        assertNotNull(dto.getUsername());
    }

    @Test
    void userSystemShowDtoEmail()
    {
        var dto = new UserSystemShowDto();
        dto.setEmail("DEMO@GMAIL.COM");
        assertNotNull(dto.getEmail());
    }

    @Test
    void userSystemShowDtoToString()
    {
        var dto = new UserSystemShowDto();
        dto.setName("DEMO");
        dto.setUsername("DEMO");
        dto.setEmail("DEMO@GMAIL.COM");
        assertNotNull(dto.toString());
    }
}