package com.applaudo.studios.moviestore;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MoviestoreApplication
{
    public static void main(String[] args) {
        SpringApplication.run(MoviestoreApplication.class, args);
    }
}
