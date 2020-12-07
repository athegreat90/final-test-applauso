package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.dto.RecoverPasswordDto;
import com.applaudo.studios.moviestore.dto.ResponseGenericDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.applaudo.studios.moviestore.entity.UserRoles;
import com.applaudo.studios.moviestore.entity.UserSystem;
import com.applaudo.studios.moviestore.repository.IRedisRepo;
import com.applaudo.studios.moviestore.repository.IUserRolesRepo;
import com.applaudo.studios.moviestore.repository.IUserSystemRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUserSystemRepo iUserSystemRepo;

    @Autowired
    private IUserRolesRepo iUserRolesRepo;

    @Autowired
    IRedisRepo iRedisRepo;

    UserSystemDto body;
    ObjectMapper mapper;

    @BeforeEach
    void init()
    {
        body = new UserSystemDto();
        body.setUsername("existentuser");
        body.setPassword("password");
        body.setEmail("demo@gmail.com");
        body.setName("demo");

        mapper = new ObjectMapper();
    }

    @Test
//    @WithMockUser(username = "existentuser", password = "password", roles = "USER")
    @WithMockUser()
    @Order(1)
    void save() throws Exception
    {
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/signup";
        mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    @Order(2)
    void login() throws Exception
    {
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/login";
        ResultActions perform = mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON));
        String contentResponse = perform.andReturn().getResponse().getContentAsString();
        ResponseGenericDto<String> response = mapper.readValue(contentResponse, ResponseGenericDto.class);
        perform.andExpect(status().isOk());
        assertNotNull(response.getBody());

        List<UserRoles> userRolesByUserId = iUserRolesRepo.findUserRolesByUserId(body.getUsername());
        System.out.println(mapper.writeValueAsString(userRolesByUserId.stream().map(UserRoles::getRoleId)));
        iUserRolesRepo.deleteAll(userRolesByUserId);

        Optional<UserSystem> userSystem = iUserSystemRepo.findById(body.getUsername());
        iUserSystemRepo.delete(userSystem.get());
    }

    @Test
    @WithMockUser()
    @Order(3)
    void forgotPassword() throws Exception
    {
        body.setUsername("demo1");
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/forgot";
        mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    @Order(4)
    void recoverPassword() throws Exception
    {
        var dto = new RecoverPasswordDto();
        dto.setUsername("demo1");
        var key = String.format("%s_forgot_password", dto.getUsername());
        var content = this.iRedisRepo.getKey(key);
        dto.setToken(content);
        dto.setNewPassword("Demo123");
        String json = mapper.writeValueAsString(dto);
        var url = "/api/v1/auth/recover";
        mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}