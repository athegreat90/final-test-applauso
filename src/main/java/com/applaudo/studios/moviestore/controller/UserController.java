package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.dto.MovieDto;
import com.applaudo.studios.moviestore.dto.ResponseGenericDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.applaudo.studios.moviestore.dto.UserSystemShowDto;
import com.applaudo.studios.moviestore.service.IUserSystemService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final IUserSystemService userSystemService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseGenericDto<List<UserSystemShowDto>> getAll()
    {
        return new ResponseGenericDto<>(0, "OK", this.userSystemService.getAll());
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseGenericDto<UserSystemShowDto> getById(HttpServletRequest httpServletRequest, @PathVariable("id") String id) throws NotFoundException
    {
        return new ResponseGenericDto<>(0, "OK", this.userSystemService.getById(id));
    }


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseGenericDto<UserSystemDto> update(HttpServletRequest httpServletRequest, @PathVariable("id") String id, @RequestBody @Valid UserSystemDto req) throws NotFoundException
    {
        logger.info("BODY: {}", req);
        String msg = String.format("The movie with id: %s was updated", id);
        return new ResponseGenericDto<>(0, msg, userSystemService.update(req, id));
    }
}
