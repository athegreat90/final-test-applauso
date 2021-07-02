package com.applaudo.studios.moviestore.controller.graphql;

import com.applaudo.studios.moviestore.config.GraphQLProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v2/movie")
@RequiredArgsConstructor
public class MovieGraphController
{
    private final GraphQLProvider graphQLProvider;

    @PostMapping("/")
    public Object listMovies(@RequestBody String query)
    {
        return graphQLProvider.graphQL().execute(query).getData();
    }
}
