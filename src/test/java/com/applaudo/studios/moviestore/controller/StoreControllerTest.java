package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StoreControllerTest
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
    void buy() throws Exception
    {
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/login";
        ResultActions perform = mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON));
        String contentResponse = perform.andReturn().getResponse().getContentAsString();
        ResponseGenericDto<String> response = mapper.readValue(contentResponse, ResponseGenericDto.class);
        perform.andExpect(status().isOk());
        String token = response.getBody();

        var dto = new MovieDto();
        dto.setId(1);
        var orderDetailDto = new OrderMovieDetailDto(dto, 1);
        var req = new UserRequestOrderDto(body, List.of(orderDetailDto));
        json = mapper.writeValueAsString(req);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/store/buy")
                .header("Authorization", "Bearer " + token).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void rent() throws Exception
    {
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/login";
        ResultActions perform = mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON));
        String contentResponse = perform.andReturn().getResponse().getContentAsString();
        ResponseGenericDto<String> response = mapper.readValue(contentResponse, ResponseGenericDto.class);
        perform.andExpect(status().isOk());
        String token = response.getBody();

        json = "{\"buyer\":{\"username\":\"athegreat90\"},\"movieList\":[{\"movie\":{\"id\":3,\"title\":\"Black Widow\",\"description\":\"\",\"stock\":30,\"rentalPrice\":10,\"salePrice\":30},\"dateBegin\":\"2020-12-05T13:10:30\",\"dateEnd\":\"2020-12-08T13:10:30\"}]}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/store/rent")
                .header("Authorization", "Bearer " + token).content(json).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void like() throws Exception
    {
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/login";
        ResultActions perform = mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON));
        String contentResponse = perform.andReturn().getResponse().getContentAsString();
        ResponseGenericDto<String> response = mapper.readValue(contentResponse, ResponseGenericDto.class);
        perform.andExpect(status().isOk());
        String token = response.getBody();

        var dto = new MovieDto();
        dto.setId(1);
        var req = new UserLikeDto(body, List.of(dto));
        json = mapper.writeValueAsString(req);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/store/like")
                .header("Authorization", "Bearer " + token).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}