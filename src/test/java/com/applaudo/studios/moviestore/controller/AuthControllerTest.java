package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.dto.RecoverPasswordDto;
import com.applaudo.studios.moviestore.dto.ResponseGenericDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.applaudo.studios.moviestore.repository.IRedisRepo;
import com.applaudo.studios.moviestore.repository.IUserRolesRepo;
import com.applaudo.studios.moviestore.repository.IUserSystemRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
@ExtendWith(MockitoExtension.class)
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

    @MockBean
    private JavaMailSender javaMailSender;


    @MockBean
    RedisTemplate<String, Object> redisTemplate;

    private @Mock RedisConnectionFactory connectionFactoryMock;
    private @Mock RedisConnection redisConnectionMock;

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

        redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactoryMock);
        when(connectionFactoryMock.getConnection()).thenReturn(redisConnectionMock);

        redisTemplate.afterPropertiesSet();
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
    }

    @Test
    @WithMockUser()
    @Order(3)
    void forgotPassword() throws Exception
    {
        String jsonRegister = mapper.writeValueAsString(body);
        var urlRegister = "/api/v1/auth/signup";
        mockMvc.perform(post(urlRegister).content(jsonRegister).contentType(MediaType.APPLICATION_JSON));

        body.setName("demo1");
        String json = mapper.writeValueAsString(body);
        var url = "/api/v1/auth/forgot";
        mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    @Order(4)
    void recoverPassword() throws Exception
    {
        String jsonRegister = mapper.writeValueAsString(body);
        var urlRegister = "/api/v1/auth/signup";
        mockMvc.perform(post(urlRegister).content(jsonRegister).contentType(MediaType.APPLICATION_JSON));

        body.setName("demo1");
        String jsonForgot = mapper.writeValueAsString(body);
        var urlForgot = "/api/v1/auth/forgot";
        mockMvc.perform(post(urlForgot).content(jsonForgot).contentType(MediaType.APPLICATION_JSON));

        var dto = new RecoverPasswordDto();
        dto.setNewPassword("password1");
        var key = String.format("%s_forgot_password", dto.getUsername());
        var content = this.iRedisRepo.getKey(key);
        dto.setToken(content);
        dto.setNewPassword("Demo123");
        String json = mapper.writeValueAsString(dto);
        var url = "/api/v1/auth/recover";
        mockMvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}