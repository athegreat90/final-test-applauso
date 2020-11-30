package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.dto.MovieDto;
import com.applaudo.studios.moviestore.dto.ResponseGenericDto;
import com.applaudo.studios.moviestore.service.IMovieService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/movie")
@AllArgsConstructor
public class MovieController
{
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    private final IMovieService movieService;

    @GetMapping("/")
    public ResponseGenericDto<List<MovieDto>> getAll()
    {
        return new ResponseGenericDto<>(0, "OK", this.movieService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseGenericDto<MovieDto> getById(HttpServletRequest httpServletRequest, @PathVariable("id") Integer id) throws NotFoundException
    {
        return new ResponseGenericDto<>(0, "OK", this.movieService.getById(id));
    }

    @PostMapping("/")
    public ResponseGenericDto<String> save(HttpServletRequest httpServletRequest, @RequestBody @Valid MovieDto req)
    {
        logger.info("BODY: {}", req);
        var id = this.movieService.save(req);
        String msg = String.format("The movie with id: %s was saved", id);
        return new ResponseGenericDto<>(0, "OK", msg);
    }

    @PutMapping("/{id}")
    public ResponseGenericDto<MovieDto> update(HttpServletRequest httpServletRequest, @PathVariable("id") Integer id, @RequestBody @Valid MovieDto req) throws NotFoundException
    {
        logger.info("BODY: {}", req);
        String msg = String.format("The movie with id: %s was updated", id);

        return new ResponseGenericDto<>(0, msg, movieService.update(req, id));
    }
}
