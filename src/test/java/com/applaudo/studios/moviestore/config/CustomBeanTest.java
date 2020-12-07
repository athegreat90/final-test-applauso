package com.applaudo.studios.moviestore.config;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomBeanTest
{
    @Autowired
    private CustomBean customBean;

    @Test
    void encoder()
    {
        assertNotNull(customBean.encoder());
    }

    @Test
    void getModelMapper()
    {
        assertNotNull(customBean.getModelMapper());
    }
}