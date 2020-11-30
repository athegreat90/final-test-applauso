package com.applaudo.studios.moviestore.service;

import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.applaudo.studios.moviestore.dto.UserSystemShowDto;
import javassist.NotFoundException;

import java.util.List;

public interface IUserSystemService
{
    public List<UserSystemShowDto> getAll();

    public UserSystemShowDto getById(String username) throws NotFoundException;

    public String save(UserSystemDto body);

    public UserSystemDto update(UserSystemDto body, String username) throws NotFoundException;

    public Boolean delete(String username) throws NotFoundException;
}
