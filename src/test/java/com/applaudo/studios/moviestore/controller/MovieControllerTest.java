package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.dto.MovieDto;
import com.applaudo.studios.moviestore.dto.ResponseGenericDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieController movieController;

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
    @WithMockUser()
    @Order(1)
    void getAll() throws Exception
    {
        String url = "/api/v1/movie/";
        ResultActions perform = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));
        perform.andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void getById() throws Exception
    {
        String url = "/api/v1/movie/detail/1";
        ResultActions perform = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));
        perform.andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void filter() throws Exception
    {
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/login";
        ResultActions perform = mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON));
        String contentResponse = perform.andReturn().getResponse().getContentAsString();
        ResponseGenericDto<String> response = mapper.readValue(contentResponse, ResponseGenericDto.class);
        perform.andExpect(status().isOk());
        String token = response.getBody();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/list?name=Avengers")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void save() throws Exception
    {
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/login";
        ResultActions perform = mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON));
        String contentResponse = perform.andReturn().getResponse().getContentAsString();
        ResponseGenericDto<String> response = mapper.readValue(contentResponse, ResponseGenericDto.class);
        perform.andExpect(status().isOk());
        String token = response.getBody();

        var dto = new MovieDto();
        dto.setTitle("DEMO");
        dto.setAvailability(Boolean.TRUE);
        dto.setDescription("DEMO");
        dto.setRentalPrice(1.2D);
        dto.setSalePrice(20D);
        dto.setStock(1);

        json = mapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movie/")
                .header("Authorization", "Bearer " + token).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @Order(5)
    void update() throws Exception
    {
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/login";
        ResultActions perform = mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON));
        String contentResponse = perform.andReturn().getResponse().getContentAsString();
        ResponseGenericDto<String> response = mapper.readValue(contentResponse, ResponseGenericDto.class);
        perform.andExpect(status().isOk());
        String token = response.getBody();

        var criteriaResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/list?name=DEMO").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        var criteriaResponseBody = criteriaResponse.andReturn().getResponse().getContentAsString();
        ResponseGenericDto<List<MovieDto>> listMovies = mapper.readValue(criteriaResponseBody, ResponseGenericDto.class);
        var moviesString= mapper.writeValueAsString(listMovies.getBody().get(0));
        MovieDto movie = mapper.readValue(moviesString, MovieDto.class);
        url = "/api/v1/movie/" + movie.getId();

        var dto = movie;
        dto.setStock(2);

        json = mapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders.put(url)
                .header("Authorization", "Bearer " + token).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}