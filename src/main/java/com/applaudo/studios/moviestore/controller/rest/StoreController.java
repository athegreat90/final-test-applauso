package com.applaudo.studios.moviestore.controller.rest;

import com.applaudo.studios.moviestore.dto.ResponseGenericDto;
import com.applaudo.studios.moviestore.dto.UserLikeDto;
import com.applaudo.studios.moviestore.dto.UserRequestOrderDto;
import com.applaudo.studios.moviestore.dto.UserRequestRentOrderDto;
import com.applaudo.studios.moviestore.service.rest.IStoreService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/store")
@AllArgsConstructor
public class StoreController
{
    private final IStoreService iStoreService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/buy")
    public ResponseGenericDto<String> buy(HttpServletRequest httpServletRequest, @RequestBody UserRequestOrderDto body)
    {
        return new ResponseGenericDto<>(0, "OK", this.iStoreService.buy(body));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/rent")
    public ResponseGenericDto<String> rent(HttpServletRequest httpServletRequest, @RequestBody UserRequestRentOrderDto body)
    {
        return new ResponseGenericDto<>(0, "OK", this.iStoreService.rent(body));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/like")
    public ResponseGenericDto<String> like(HttpServletRequest httpServletRequest, @RequestBody UserLikeDto body)
    {
        return new ResponseGenericDto<>(0, "OK", this.iStoreService.like(body));
    }
}
