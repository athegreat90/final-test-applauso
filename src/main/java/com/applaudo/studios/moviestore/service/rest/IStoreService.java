package com.applaudo.studios.moviestore.service.rest;

import com.applaudo.studios.moviestore.dto.UserLikeDto;
import com.applaudo.studios.moviestore.dto.UserRequestOrderDto;
import com.applaudo.studios.moviestore.dto.UserRequestRentOrderDto;

public interface IStoreService
{
    String buy(UserRequestOrderDto body);

    String rent(UserRequestRentOrderDto body);

    String like(UserLikeDto body);
}
