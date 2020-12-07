package com.applaudo.studios.moviestore.service;

import com.applaudo.studios.moviestore.dto.MovieDto;
import com.applaudo.studios.moviestore.dto.UserLikeDto;
import com.applaudo.studios.moviestore.dto.UserRequestOrderDto;
import com.applaudo.studios.moviestore.dto.UserRequestRentOrderDto;
import com.applaudo.studios.moviestore.entity.*;
import com.applaudo.studios.moviestore.repository.IUserMovieLikeRepo;
import com.applaudo.studios.moviestore.repository.IUserOrderRepo;
import com.applaudo.studios.moviestore.repository.IUserRentRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements IStoreService
{
    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    private final IUserMovieLikeRepo iUserMovieLikeRepo;
    private final IUserOrderRepo iUserOrderRepo;
    private final IUserRentRepo iUserRentRepo;
    private final ModelMapper modelMapper;

    @Override
    public String buy(UserRequestOrderDto body)
    {
        List<UserOrder> orders = body.getMovieList().stream().map(orderMovieDetailDto ->
        {
            logger.info("Movie: {}", orderMovieDetailDto.getMovie());
            var userOrder = new UserOrder();
            userOrder.setCount(Double.valueOf(orderMovieDetailDto.getCount()));
            userOrder.setMovieByIdMovie(this.modelMapper.map(orderMovieDetailDto.getMovie(), Movie.class));
            userOrder.setUserSystemByUsername(this.modelMapper.map(body.getBuyer(), UserSystem.class));
            return userOrder;
        }).collect(Collectors.toList());
        orders = this.iUserOrderRepo.saveAll(orders);
        List<String> ids = orders.stream().map(userOrder -> String.valueOf(userOrder.getId())).collect(Collectors.toList());
        return String.format("The orders to buy: %s was generated successfully", String.join(", ", ids));
    }

    @Override
    public String rent(UserRequestRentOrderDto body)
    {
        List<UserRent> orders = body.getMovieList().stream().map(orderMovieDetailRentDto ->
        {
            logger.info("orderMovieDetailRentDto: {}", orderMovieDetailRentDto);
            var userOrder = new UserRent();
            userOrder.setUserSystemByUsername(this.modelMapper.map(body.getBuyer(), UserSystem.class));
            userOrder.setMovieByIdMovie(this.modelMapper.map(orderMovieDetailRentDto.getMovie(), Movie.class));

            // Creating Instant instance from localDateTime time
            Instant instantBegin = orderMovieDetailRentDto.getDateBegin().atZone(ZoneId.systemDefault()).toInstant();
            Instant instantEnd = orderMovieDetailRentDto.getDateEnd().atZone(ZoneId.systemDefault()).toInstant();

            userOrder.setDateBegin(Date.from(instantBegin));
            userOrder.setDateEnd(Date.from(instantEnd));
            logger.info("UserOrder: {}", userOrder);
            return userOrder;
        }).collect(Collectors.toList());
        orders = this.iUserRentRepo.saveAll(orders);
        List<String> ids = orders.stream().map(userOrder -> String.valueOf(userOrder.getId())).collect(Collectors.toList());
        return String.format("The orders to rent: %s was generated successfully", String.join(", ", ids));
    }

    @Override
    public String like(UserLikeDto body)
    {
        var ids = new ArrayList<String>();
        body.getListMovies().forEach(movieDto ->
        {
            var userMovieLike = new UserMovieLike();
            userMovieLike.setMovieByIdMovie(this.modelMapper.map(movieDto, Movie.class));
            userMovieLike.setUserSystemByUsername(this.modelMapper.map(body.getUser(), UserSystem.class));
            userMovieLike = this.iUserMovieLikeRepo.saveAndFlush(userMovieLike);
            ids.add(String.valueOf(userMovieLike.getId()));
        });
        return String.format("Do yo liked the following movies: %s", String.join(", ", body.getListMovies().stream().map(MovieDto::getTitle).collect(Collectors.toList())));
    }
}
