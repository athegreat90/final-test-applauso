package com.applaudo.studios.moviestore.service.rest;

import com.applaudo.studios.moviestore.config.TokenProvider;
import com.applaudo.studios.moviestore.dto.CriteriaMovieDto;
import com.applaudo.studios.moviestore.dto.MovieDto;
import com.applaudo.studios.moviestore.entity.Movie;
import com.applaudo.studios.moviestore.entity.UserRoles;
import com.applaudo.studios.moviestore.repository.IMovieRepo;
import com.applaudo.studios.moviestore.repository.IUserRolesRepo;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.applaudo.studios.moviestore.util.Const.TOKEN_PREFIX;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements IMovieService
{
    public static final String THE_ID = "The id ";
    public static final String DOESN_T_EXISTS = " doesn't exists";
    private final IMovieRepo movieRepo;
    private final ModelMapper modelMapper;
    private final TokenProvider jwtTokenUtil;
    private final IUserRolesRepo iUserRolesRepo;

    // define field for entitymanager
    private final EntityManager entityManager;

    @Override
    public List<MovieDto> getAll()
    {
        List<Movie> movieList = this.movieRepo.findAll();
        Type listType = new TypeToken<List<MovieDto>>() {}.getType();
        return this.modelMapper.map(movieList, listType);
    }

    @Override
    public List<MovieDto> getByCriteria(CriteriaMovieDto criteria, String token)
    {
        var authToken = token.replace(TOKEN_PREFIX,"");
        var username = jwtTokenUtil.getUsernameFromToken(authToken);
        List<UserRoles> roles = this.iUserRolesRepo.findUserRolesByUserId(username);
        var size = roles.stream().filter(userRoles -> userRoles.getRoleByRoleId().getId() == 1).collect(Collectors.toList()).size();
        if (size == 1)
        {
            criteria.setAvailability(Boolean.TRUE);
        }
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);
        var sql = "SELECT DISTINCT m.* FROM accion_finaltest.movie m ";

        if (criteria.getLiked() != null)
        {
            sql += "INNER JOIN accion_finaltest.user_movie_like uml ON uml.id_movie = m.id ";
        }

        if (!StringUtils.isEmpty(criteria.getName()) || criteria.getMinPrice() != null || criteria.getMaxPrice() != null || criteria.getAvailability() != null || criteria.getLiked() != null)
        {
            sql += "WHERE ";
        }

        if (criteria.getLiked() != null)
        {
            sql += "uml.username = :username AND ";
        }

        if (!StringUtils.isEmpty(criteria.getName()))
        {
            sql += "m.title LIKE :name AND ";
        }

        if (criteria.getMinPrice() != null)
        {
            sql += "m.sale_price > :minprice AND ";
        }

        if (criteria.getMaxPrice() != null)
        {
            sql += "m.sale_price < :maxprice AND ";
        }

        if (criteria.getAvailability() != null)
        {
            sql += "m.availability = :availability";
        }

        if (sql.endsWith("AND "))
        {
            sql = sql.substring(0, sql.length() - 4).trim();
        }

        NativeQuery nativeQuery = currentSession.createSQLQuery(sql);

        if (!StringUtils.isEmpty(criteria.getName()))
        {
            nativeQuery.setParameter("name", "%" + criteria.getName() + "%");
        }

        if (criteria.getMinPrice() != null)
        {
            nativeQuery.setParameter("minprice", criteria.getMinPrice());
        }

        if (criteria.getMaxPrice() != null)
        {
            nativeQuery.setParameter("maxprice", criteria.getMaxPrice());
        }

        if (criteria.getAvailability() != null)
        {
            nativeQuery.setParameter("availability", criteria.getAvailability());
        }

        if (criteria.getLiked() != null)
        {
            nativeQuery.setParameter("username", username);
        }

        nativeQuery.addEntity(Movie.class);
        List<Movie> movies = nativeQuery.getResultList();

        Type listType = new TypeToken<List<MovieDto>>() {}.getType();
        return this.modelMapper.map(movies, listType);
    }

    @Override
    public MovieDto getById(Integer idMovie) throws NotFoundException
    {
        Optional<Movie> original = this.movieRepo.findById(idMovie);
        if (original.isPresent())
        {
            return this.modelMapper.map(original.get(), MovieDto.class);
        }
        else
        {
            throw new NotFoundException(THE_ID + idMovie + DOESN_T_EXISTS);
        }
    }

    @Override
    public Integer save(MovieDto body)
    {
        Movie movie = this.modelMapper.map(body, Movie.class);
        Movie movieSaved = this.movieRepo.saveAndFlush(movie);
        return movieSaved.getId();
    }

    @Override
    public MovieDto update(MovieDto body, Integer idMovie) throws NotFoundException
    {
        Optional<Movie> original = this.movieRepo.findById(idMovie);
        if (original.isPresent())
        {
            body.setId(idMovie);
            Movie movie = this.modelMapper.map(body, Movie.class);
            Movie movieSaved = this.movieRepo.saveAndFlush(movie);
            return this.modelMapper.map(movieSaved, MovieDto.class);
        }
        else
        {
            throw new NotFoundException(THE_ID + idMovie + DOESN_T_EXISTS);
        }
    }

    @Override
    public Boolean delete(Integer idMovie) throws NotFoundException
    {
        Optional<Movie> original = this.movieRepo.findById(idMovie);
        if (original.isPresent())
        {
            this.movieRepo.delete(original.get());
            return Boolean.TRUE;
        }
        else
        {
            throw new NotFoundException(String.format("%s%d%s", THE_ID, idMovie, DOESN_T_EXISTS));
        }
    }
}
