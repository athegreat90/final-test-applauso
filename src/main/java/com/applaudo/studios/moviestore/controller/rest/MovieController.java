package com.applaudo.studios.moviestore.controller.rest;

import com.applaudo.studios.moviestore.dto.CriteriaMovieDto;
import com.applaudo.studios.moviestore.dto.MovieDto;
import com.applaudo.studios.moviestore.dto.ResponseGenericDto;
import com.applaudo.studios.moviestore.service.rest.IMovieService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static com.applaudo.studios.moviestore.util.Const.HEADER_STRING;

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

    @GetMapping("/detail/{id}")
    public ResponseGenericDto<MovieDto> getById(HttpServletRequest httpServletRequest, @PathVariable("id") Integer id) throws NotFoundException
    {
        return new ResponseGenericDto<>(0, "OK", this.movieService.getById(id));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/list")
    public ResponseGenericDto<List<MovieDto>> filter(HttpServletRequest httpServletRequest, @RequestParam(required = false) String name, @RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice, @RequestParam(required = false) Boolean availability, @RequestParam(required = false) Boolean like)
    {
        String token = httpServletRequest.getHeader(HEADER_STRING);
        var criteria = new CriteriaMovieDto();
        criteria.setName(name);
        criteria.setMinPrice(minPrice);
        criteria.setMaxPrice(maxPrice);
        criteria.setAvailability(availability);
        criteria.setLiked(like);
        var list = this.movieService.getByCriteria(criteria, token);
        return new ResponseGenericDto<>(0, "OK", list);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseGenericDto<String> save(HttpServletRequest httpServletRequest, @RequestBody @Valid MovieDto req)
    {
        logger.info("BODY: {}", req);
        var id = this.movieService.save(req);
        String msg = String.format("The movie with id: %s was saved", id);
        return new ResponseGenericDto<>(0, "OK", msg);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseGenericDto<MovieDto> update(HttpServletRequest httpServletRequest, @PathVariable("id") Integer id, @RequestBody @Valid MovieDto req) throws NotFoundException
    {
        logger.info("BODY: {}", req);
        String msg = String.format("The movie with id: %s was updated", id);

        return new ResponseGenericDto<>(0, msg, movieService.update(req, id));
    }
}
