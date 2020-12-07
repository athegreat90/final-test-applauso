package com.applaudo.studios.moviestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDto
{
    private Integer id;
    private String description;
    private String name;
}
