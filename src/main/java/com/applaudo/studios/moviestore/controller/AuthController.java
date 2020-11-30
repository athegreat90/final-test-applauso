package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.config.TokenProvider;
import com.applaudo.studios.moviestore.dto.ResponseGenericDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.applaudo.studios.moviestore.service.IUserSystemService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final IUserSystemService userSystemService;

    private final AuthenticationManager authenticationManager;

    private final TokenProvider jwtTokenUtil;

    @PostMapping("/signup")
    public ResponseGenericDto<String> save(HttpServletRequest httpServletRequest, @RequestBody @Valid UserSystemDto req)
    {
        var id = this.userSystemService.save(req);
        String msg = String.format("The user with id: %s was saved", id);
        return new ResponseGenericDto<>(0, "OK", msg);
    }

    @PostMapping("/login")
    public ResponseGenericDto<String> login(HttpServletRequest httpServletRequest, @RequestBody @Valid UserSystemDto req)
    {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return new ResponseGenericDto<>(0, "OK", token);
    }
}
