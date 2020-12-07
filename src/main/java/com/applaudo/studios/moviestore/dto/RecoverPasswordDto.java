package com.applaudo.studios.moviestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecoverPasswordDto
{
    private String username;
    private String token;
    private String newPassword;
}
