package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.dto.MovieDto;
import com.applaudo.studios.moviestore.dto.ResponseGenericDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.applaudo.studios.moviestore.service.IUserSystemService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
@AllArgsConstructor
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    private final IUserSystemService userSystemService;

    @GetMapping("/")
    public ResponseGenericDto<List<UserSystemDto>> getAll()
    {
        return new ResponseGenericDto<>(0, "OK", this.userSystemService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseGenericDto<UserSystemDto> getById(HttpServletRequest httpServletRequest, @PathVariable("id") String id) throws NotFoundException
    {
        return new ResponseGenericDto<>(0, "OK", this.userSystemService.getById(id));
    }

    @PostMapping("/")
    public ResponseGenericDto<String> save(HttpServletRequest httpServletRequest, @RequestBody @Valid UserSystemDto req)
    {
        logger.info("BODY: {}", req);
        var id = this.userSystemService.save(req);
        String msg = String.format("The movie with id: %s was saved", id);
        return new ResponseGenericDto<>(0, "OK", msg);
    }

    @PutMapping("/{id}")
    public ResponseGenericDto<UserSystemDto> update(HttpServletRequest httpServletRequest, @PathVariable("id") String id, @RequestBody @Valid UserSystemDto req) throws NotFoundException
    {
        logger.info("BODY: {}", req);
        String msg = String.format("The movie with id: %s was updated", id);
        return new ResponseGenericDto<>(0, msg, userSystemService.update(req, id));
    }
}
