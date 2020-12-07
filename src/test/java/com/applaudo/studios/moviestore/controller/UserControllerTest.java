package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.dto.ResponseGenericDto;
import com.applaudo.studios.moviestore.dto.RoleDto;
import com.applaudo.studios.moviestore.dto.UserRepoReqDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    UserSystemDto body;
    ObjectMapper mapper;

    @BeforeEach
    void init()
    {
        body = new UserSystemDto();
        body.setUsername("athegreat90");
        body.setPassword("Demo123");
        body.setEmail("demo@gmail.com");
        body.setName("demo");

        mapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void getAll() throws Exception
    {
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/login";
        ResultActions perform = mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON));
        String contentResponse = perform.andReturn().getResponse().getContentAsString();
        ResponseGenericDto<String> response = mapper.readValue(contentResponse, ResponseGenericDto.class);
        perform.andExpect(status().isOk());
        String token = response.getBody();

        url = "/api/v1/user/";
        mockMvc.perform(get(url).header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void getById() throws Exception
    {
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/login";
        ResultActions perform = mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON));
        String contentResponse = perform.andReturn().getResponse().getContentAsString();
        ResponseGenericDto<String> response = mapper.readValue(contentResponse, ResponseGenericDto.class);
        perform.andExpect(status().isOk());
        String token = response.getBody();

        url = "/api/v1/user/" + body.getUsername();
        mockMvc.perform(get(url).header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void update() throws Exception
    {
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/login";
        ResultActions perform = mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON));
        String contentResponse = perform.andReturn().getResponse().getContentAsString();
        ResponseGenericDto<String> response = mapper.readValue(contentResponse, ResponseGenericDto.class);
        perform.andExpect(status().isOk());
        String token = response.getBody();
        body.setName("A. Alexander A. Mora");
        url = "/api/v1/user/" + body.getUsername();
        json = mapper.writeValueAsString(body);
        mockMvc.perform(put(url).header("Authorization", "Bearer " + token).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void addRole() throws Exception
    {
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/login";
        ResultActions perform = mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON));
        String contentResponse = perform.andReturn().getResponse().getContentAsString();
        ResponseGenericDto<String> response = mapper.readValue(contentResponse, ResponseGenericDto.class);
        perform.andExpect(status().isOk());
        String token = response.getBody();

        var req = new UserRepoReqDto();
        req.setUserDetail(body);
        req.setRoles(List.of(new RoleDto(2, "", ""), new RoleDto(1, "", "")));

        url = "/api/v1/user/role/" + body.getUsername();
        json = mapper.writeValueAsString(req);
        mockMvc.perform(post(url).header("Authorization", "Bearer " + token).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void changeRole() throws Exception
    {
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/login";
        ResultActions perform = mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON));
        String contentResponse = perform.andReturn().getResponse().getContentAsString();
        ResponseGenericDto<String> response = mapper.readValue(contentResponse, ResponseGenericDto.class);
        perform.andExpect(status().isOk());
        String token = response.getBody();

        var req = new UserRepoReqDto();
        req.setUserDetail(body);
        req.setRoles(List.of(new RoleDto(2, "", ""), new RoleDto(1, "", "")));

        url = "/api/v1/user/role/" + body.getUsername();
        json = mapper.writeValueAsString(req);
        mockMvc.perform(put(url).header("Authorization", "Bearer " + token).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Order(6)
    void deleteRole() throws Exception
    {
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/login";
        ResultActions perform = mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON));
        String contentResponse = perform.andReturn().getResponse().getContentAsString();
        ResponseGenericDto<String> response = mapper.readValue(contentResponse, ResponseGenericDto.class);
        perform.andExpect(status().isOk());
        String token = response.getBody();

        var req = new UserRepoReqDto();
        req.setUserDetail(body);
        req.setRoles(List.of(new RoleDto(2, "", "")));

        url = "/api/v1/user/role/delete/" + body.getUsername();
        json = mapper.writeValueAsString(req);
        mockMvc.perform(put(url).header("Authorization", "Bearer " + token).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}