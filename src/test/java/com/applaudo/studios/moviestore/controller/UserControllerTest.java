package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.config.TestRedisConfiguration;
import com.applaudo.studios.moviestore.config.TokenProvider;
import com.applaudo.studios.moviestore.dto.ResponseGenericDto;
import com.applaudo.studios.moviestore.dto.RoleDto;
import com.applaudo.studios.moviestore.dto.UserRepoReqDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.applaudo.studios.moviestore.entity.Role;
import com.applaudo.studios.moviestore.entity.UserRoles;
import com.applaudo.studios.moviestore.entity.UserRolesPK;
import com.applaudo.studios.moviestore.entity.UserSystem;
import com.applaudo.studios.moviestore.repository.IRoleRepo;
import com.applaudo.studios.moviestore.repository.IUserRolesRepo;
import com.applaudo.studios.moviestore.repository.IUserSystemRepo;
import com.applaudo.studios.moviestore.service.rest.IMovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestRedisConfiguration.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest
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

        doReturn(body.getUsername()).when(jwtTokenUtil).getUsernameFromToken(anyString());

        doReturn(Boolean.TRUE).when(jwtTokenUtil).validateToken(anyString(), any());

        var authentication = new UsernamePasswordAuthenticationToken(user, "", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        doReturn(authentication).when(jwtTokenUtil).getAuthentication(anyString(), any(), any());
    }

    @Test
    @Order(1)
    void getAll() throws Exception
    {
        var url = "/api/v1/user/";
        mockMvc.perform(get(url).header("Authorization", "Bearer abc123").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void getById() throws Exception
    {
        var url = "/api/v1/user/" + body.getUsername();
        mockMvc.perform(get(url).header("Authorization", "Bearer abc123").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void update() throws Exception
    {
        body.setName("A. Alexander A. Mora");
        var url = "/api/v1/user/" + body.getUsername();
        var json = mapper.writeValueAsString(body);
        mockMvc.perform(put(url).header("Authorization", "Bearer abc123").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void addRole() throws Exception
    {
        var req = new UserRepoReqDto();
        req.setUserDetail(body);
        req.setRoles(List.of(new RoleDto(2, "", ""), new RoleDto(1, "", "")));

        var url = "/api/v1/user/role/" + body.getUsername();
        var json = mapper.writeValueAsString(req);
        mockMvc.perform(post(url).header("Authorization", "Bearer abc123").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void changeRole() throws Exception
    {
        var req = new UserRepoReqDto();
        req.setUserDetail(body);
        req.setRoles(List.of(new RoleDto(2, "", ""), new RoleDto(1, "", "")));

        var url = "/api/v1/user/role/" + body.getUsername();
        var json = mapper.writeValueAsString(req);
        mockMvc.perform(put(url).header("Authorization", "Bearer abc123").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Order(6)
    void deleteRole() throws Exception
    {
        var req = new UserRepoReqDto();
        req.setUserDetail(body);
        req.setRoles(List.of(new RoleDto(2, "", "")));

        var url = "/api/v1/user/role/delete/" + body.getUsername();
        var json = mapper.writeValueAsString(req);
        mockMvc.perform(put(url).header("Authorization", "Bearer abc123").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}