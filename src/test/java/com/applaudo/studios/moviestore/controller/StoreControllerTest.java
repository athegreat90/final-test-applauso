package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.config.TestRedisConfiguration;
import com.applaudo.studios.moviestore.config.TokenProvider;
import com.applaudo.studios.moviestore.dto.*;
import com.applaudo.studios.moviestore.entity.Role;
import com.applaudo.studios.moviestore.entity.UserRoles;
import com.applaudo.studios.moviestore.entity.UserRolesPK;
import com.applaudo.studios.moviestore.entity.UserSystem;
import com.applaudo.studios.moviestore.repository.IRoleRepo;
import com.applaudo.studios.moviestore.repository.IUserRolesRepo;
import com.applaudo.studios.moviestore.repository.IUserSystemRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StoreControllerTest
{
    public static final String PASSWORD = "Demo123";
    public static final String USERNAME = "athegreat90";

    @Autowired
    private MockMvc mockMvc;

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

        doReturn(body.getUsername()).when(jwtTokenUtil).getUsernameFromToken(anyString());

        doReturn(Boolean.TRUE).when(jwtTokenUtil).validateToken(anyString(), any());

        var authentication = new UsernamePasswordAuthenticationToken(user, "", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        doReturn(authentication).when(jwtTokenUtil).getAuthentication(anyString(), any(), any());
    }

    @Test
    void buy() throws Exception
    {
        var dto = new MovieDto();
        dto.setId(1);
        dto.setTitle("DEMO");
        dto.setAvailability(Boolean.TRUE);
        dto.setDescription("DEMO");
        dto.setRentalPrice(1.2D);
        dto.setSalePrice(20D);
        dto.setStock(1);

        var json = mapper.writeValueAsString(dto);





        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movie/")
                        .header("Authorization", "Bearer abc123").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        dto.setId(1);
        var orderDetailDto = new OrderMovieDetailDto(dto, 1);
        var req = new UserRequestOrderDto(body, List.of(orderDetailDto));
        json = mapper.writeValueAsString(req);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/store/buy")
                .header("Authorization", "Bearer abc123").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void rent() throws Exception
    {
        var dto = new MovieDto();
        dto.setId(1);
        dto.setTitle("DEMO");
        dto.setAvailability(Boolean.TRUE);
        dto.setDescription("DEMO");
        dto.setRentalPrice(1.2D);
        dto.setSalePrice(20D);
        dto.setStock(1);

        var json = mapper.writeValueAsString(dto);





        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movie/")
                        .header("Authorization", "Bearer abc123").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        json = "{\"buyer\":{\"username\":\"athegreat90\"},\"movieList\":[{\"movie\":{\"id\":1,\"title\":\"Black Widow\",\"description\":\"\",\"stock\":30,\"rentalPrice\":10,\"salePrice\":30},\"dateBegin\":\"2020-12-05T13:10:30\",\"dateEnd\":\"2020-12-08T13:10:30\"}]}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/store/rent")
                .header("Authorization", "Bearer abc123").content(json).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void like() throws Exception
    {
        var dto = new MovieDto();
        dto.setId(1);
        dto.setTitle("DEMO");
        dto.setAvailability(Boolean.TRUE);
        dto.setDescription("DEMO");
        dto.setRentalPrice(1.2D);
        dto.setSalePrice(20D);
        dto.setStock(1);

        var json = mapper.writeValueAsString(dto);





        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movie/")
                        .header("Authorization", "Bearer abc123").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        dto.setId(1);
        var req = new UserLikeDto(body, List.of(dto));
        json = mapper.writeValueAsString(req);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/store/like")
                .header("Authorization", "Bearer 123").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}