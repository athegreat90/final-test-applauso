package com.applaudo.studios.moviestore.service;

import com.applaudo.studios.moviestore.config.TokenProvider;
import com.applaudo.studios.moviestore.dto.*;
import com.applaudo.studios.moviestore.entity.Role;
import com.applaudo.studios.moviestore.entity.UserRoles;
import com.applaudo.studios.moviestore.entity.UserRolesPK;
import com.applaudo.studios.moviestore.entity.UserSystem;
import com.applaudo.studios.moviestore.repository.IRoleRepo;
import com.applaudo.studios.moviestore.repository.IUserRolesRepo;
import com.applaudo.studios.moviestore.repository.IUserSystemRepo;
import com.applaudo.studios.moviestore.service.rest.IMovieService;
import com.applaudo.studios.moviestore.service.rest.IStoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class StoreServiceImplTest
{
    public static final String PASSWORD = "Demo123";
    public static final String USERNAME = "athegreat90";

    @Autowired
    IStoreService iStoreService;

    @Autowired
    private IRoleRepo roleRepo;

    @Autowired
    private IUserSystemRepo userSystemRepo;

    @Autowired
    private IUserRolesRepo userRolesRepo;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @SpyBean
    private TokenProvider jwtTokenUtil;

    @Autowired
    private IMovieService movieService;

    UserSystemDto body;
    ObjectMapper mapper;

    private UserSystem user = new UserSystem();

    @BeforeEach
    void init()
    {
        body = new UserSystemDto();
        body.setUsername(USERNAME);
        body.setPassword(PASSWORD);
        body.setEmail("demo@gmail.com");
        body.setName("demo");

        mapper = new ObjectMapper();

        this.configAdminTest();


    }

    private void configAdminTest()
    {
        var roles = this.roleRepo.findAll();
        var role = new Role();

        if (roles.isEmpty())
        {
            role.setId(1);
            role.setName("ADMIN");
            role.setDescription("ADMIN");
            this.roleRepo.save(role);
        }
        else
        {
            role = this.roleRepo.getById(1);
        }

        var users = this.userSystemRepo.findById(USERNAME);

        if (users.isEmpty())
        {
            user.setUsername(USERNAME);
            var passwordHash = bcryptEncoder.encode(PASSWORD);
            user.setPassword(passwordHash);
            user.setEmail("demo@gmail.com");
            user.setName("demo");

            user = this.userSystemRepo.save(user);
        }
        else
        {
            user = this.userSystemRepo.getById(body.getUsername());
        }

        var rolesByUser = this.userRolesRepo.findUserRolesByUserId(user.getUsername());

        if (rolesByUser.isEmpty())
        {
            var pkUserRoles = new UserRolesPK();
            pkUserRoles.setUserId(user.getUsername());
            pkUserRoles.setRoleId(role.getId());

            var userRoles = new UserRoles();
            userRoles.setUserId(user.getUsername());
            userRoles.setRoleId(role.getId());
            userRoles.setRoleByRoleId(role);
            userRoles.setUserSystemByUserId(user);
            this.userRolesRepo.save(userRoles);
        }
    }

    @Test
    @Disabled
    void buy()
    {
        var dto = new MovieDto();
        dto.setId(1);
        dto.setTitle("DEMO");
        dto.setAvailability(Boolean.TRUE);
//        dto.setMovieXPicturesById(List.of());
        dto.setDescription("DEMO");
        dto.setRentalPrice(1.2D);
        dto.setSalePrice(20D);
        dto.setStock(1);
        this.movieService.save(dto);

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
    @Disabled
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
    @Disabled
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