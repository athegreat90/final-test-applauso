package com.applaudo.studios.moviestore.service;

import com.applaudo.studios.moviestore.dto.MovieDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.applaudo.studios.moviestore.entity.Movie;
import com.applaudo.studios.moviestore.entity.UserSystem;
import com.applaudo.studios.moviestore.repository.IMovieRepo;
import com.applaudo.studios.moviestore.repository.IUserSystemRepo;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSystemServiceImpl implements IUserSystemService
{
    private final IUserSystemRepo userSystemRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<UserSystemDto> getAll()
    {
        List<UserSystem> userList = this.userSystemRepo.findAll();
        Type listType = new TypeToken<List<UserSystemDto>>() {}.getType();
        List<UserSystemDto> result = this.modelMapper.map(userList, listType);
        return result;
    }

    @Override
    public UserSystemDto getById(String username) throws NotFoundException
    {
        Optional<UserSystem> original = this.userSystemRepo.findById(username);
        if (original.isPresent())
        {
            return this.modelMapper.map(original.get(), UserSystemDto.class);
        }
        else
        {
            throw new NotFoundException("The id " + username + " doesn't exists");
        }
    }

    @Override
    public String save(UserSystemDto body)
    {
        UserSystem user = this.modelMapper.map(body, UserSystem.class);
        var passwordHash = new DigestUtils("SHA3-256").digestAsHex(body.getPassword());
        body.setPassword(passwordHash);
        UserSystem userSaved = this.userSystemRepo.saveAndFlush(user);
        return userSaved.getUsername();
    }

    @Override
    public UserSystemDto update(UserSystemDto body, String username) throws NotFoundException
    {
        Optional<UserSystem> original = this.userSystemRepo.findById(username);
        if (original.isPresent())
        {
            UserSystem user = this.modelMapper.map(original.get(), UserSystem.class);
            user = this.userSystemRepo.saveAndFlush(user);
            return this.modelMapper.map(user, UserSystemDto.class);
        }
        else
        {
            throw new NotFoundException("The id " + username + " doesn't exists");
        }
    }

    @Override
    public Boolean delete(String username) throws NotFoundException
    {
        Optional<UserSystem> original = this.userSystemRepo.findById(username);
        if (original.isPresent())
        {
            return Boolean.TRUE;
        }
        else
        {
            throw new NotFoundException("The id " + username + " doesn't exists");
        }
    }
}
