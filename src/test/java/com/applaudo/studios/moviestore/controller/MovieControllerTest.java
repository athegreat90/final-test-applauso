package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.config.TestRedisConfiguration;
import com.applaudo.studios.moviestore.config.TokenProvider;
import com.applaudo.studios.moviestore.controller.rest.MovieController;
import com.applaudo.studios.moviestore.dto.MovieDto;
import com.applaudo.studios.moviestore.dto.ResponseGenericDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.applaudo.studios.moviestore.entity.Role;
import com.applaudo.studios.moviestore.entity.UserRoles;
import com.applaudo.studios.moviestore.entity.UserRolesPK;
import com.applaudo.studios.moviestore.entity.UserSystem;
import com.applaudo.studios.moviestore.repository.IRoleRepo;
import com.applaudo.studios.moviestore.repository.IUserRolesRepo;
import com.applaudo.studios.moviestore.repository.IUserSystemRepo;
import com.applaudo.studios.moviestore.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.applaudo.studios.moviestore.util.JsonUtil.printJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieControllerTest
{
    public static final String PASSWORD = "Demo123";
    public static final String USERNAME = "athegreat90";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieController movieController;

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

        var users = this.userSystemRepo.findAll();

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
    @WithMockUser()
    @Order(2)
    void getAll() throws Exception
    {
        String url = "/api/v1/movie/";
        ResultActions perform = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));
        perform.andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void getById() throws Exception
    {
        String url = "/api/v1/movie/detail/1";
        ResultActions perform = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));
        perform.andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void filter() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/list?name=DEMO")
                .header("Authorization", "Bearer abc123"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(1)
    void save() throws Exception
    {
        var dto = new MovieDto();
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

    }

    @Test
    @Order(5)
    void update() throws Exception
    {
        var criteriaResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/list?name=DEMO").header("Authorization", "Bearer abc123").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        var criteriaResponseBody = criteriaResponse.andReturn().getResponse().getContentAsString();
        ResponseGenericDto<List<MovieDto>> listMovies = mapper.readValue(criteriaResponseBody, ResponseGenericDto.class);
        List<MovieDto> body = listMovies.getBody();
        var moviesString= mapper.writeValueAsString(body);
        printJson(moviesString);
        MovieDto movie = mapper.readValue(moviesString, new TypeReference<List<MovieDto>>(){}).get(0);
        var url = "/api/v1/movie/" + movie.getId();

        var dto = movie;
        dto.setStock(2);

        var json = mapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders.put(url)
                .header("Authorization", "Bearer abc123").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}