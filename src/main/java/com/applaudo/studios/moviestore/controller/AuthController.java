package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.config.TokenProvider;
import com.applaudo.studios.moviestore.dto.RecoverPasswordDto;
import com.applaudo.studios.moviestore.dto.ResponseGenericDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.applaudo.studios.moviestore.service.IUserSystemService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController
{
    private final IUserSystemService userSystemService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider jwtTokenUtil;

    @PostMapping("/signup")
    public ResponseGenericDto<String> save(HttpServletRequest httpServletRequest, @RequestBody @Valid UserSystemDto req)
    {
        var id = this.userSystemService.save(req);
        String msg = String.format("The user with username: %s was saved", id);
        return new ResponseGenericDto<>(0, "OK", msg);
    }

    @PostMapping("/login")
    public ResponseGenericDto<String> login(HttpServletRequest httpServletRequest, @RequestBody @Valid UserSystemDto req)
    {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = this.jwtTokenUtil.generateToken(authentication);
        return new ResponseGenericDto<>(0, "OK", token);
    }

    @PostMapping("/forgot")
    public ResponseGenericDto<String> forgotPassword(HttpServletRequest httpServletRequest, @RequestBody @Valid UserSystemDto req) throws NotFoundException
    {
        var response = this.userSystemService.forgot(req);
        return new ResponseGenericDto<>(0, "OK", response);
    }

    @PostMapping("/recover")
    public ResponseGenericDto<String> recoverPassword(HttpServletRequest httpServletRequest, @RequestBody @Valid RecoverPasswordDto req)
    {
        var response = this.userSystemService.recover(req);
        return new ResponseGenericDto<>(0, "OK", response);
    }
}
