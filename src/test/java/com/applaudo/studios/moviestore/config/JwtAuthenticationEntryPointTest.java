package com.applaudo.studios.moviestore.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.core.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtAuthenticationEntryPointTest
{
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Test
    void commence()
    {
        var req = Mockito.mock(HttpServletRequest.class);
        var res = Mockito.mock(HttpServletResponse.class);
        var authException = Mockito.mock(AuthenticationException.class);
        assertDoesNotThrow(() -> jwtAuthenticationEntryPoint.commence(req, res, authException));
    }
}