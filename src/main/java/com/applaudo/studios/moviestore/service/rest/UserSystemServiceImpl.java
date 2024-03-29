package com.applaudo.studios.moviestore.service.rest;

import com.applaudo.studios.moviestore.config.props.MailProperties;
import com.applaudo.studios.moviestore.config.props.RedisPropertiesCustom;
import com.applaudo.studios.moviestore.dto.RecoverPasswordDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.applaudo.studios.moviestore.dto.UserSystemShowDto;
import com.applaudo.studios.moviestore.entity.Role;
import com.applaudo.studios.moviestore.entity.UserRoles;
import com.applaudo.studios.moviestore.entity.UserSystem;
import com.applaudo.studios.moviestore.repository.IRedisRepo;
import com.applaudo.studios.moviestore.repository.IRoleRepo;
import com.applaudo.studios.moviestore.repository.IUserRolesRepo;
import com.applaudo.studios.moviestore.repository.IUserSystemRepo;
import com.applaudo.studios.moviestore.service.IEmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.*;

@Service(value = "userService")
@Slf4j
public class UserSystemServiceImpl implements UserDetailsService, IUserSystemService
{
    public static final String THE_ID = "The id ";
    public static final String DOESN_T_EXISTS = " doesn't exists";

    private final IUserSystemRepo userSystemRepo;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bcryptEncoder;

    private final IUserRolesRepo iUserRolesRepo;

    private final IRoleRepo iRoleRepo;

    private final IEmailService iEmailService;

    private final IRedisRepo iRedisRepo;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private RedisPropertiesCustom redisPropertiesCustom;

    public UserSystemServiceImpl(IUserSystemRepo userSystemRepo, ModelMapper modelMapper, BCryptPasswordEncoder bcryptEncoder, IUserRolesRepo iUserRolesRepo, IRoleRepo iRoleRepo, IEmailService iEmailService, IRedisRepo iRedisRepo) {
        this.userSystemRepo = userSystemRepo;
        this.modelMapper = modelMapper;
        this.bcryptEncoder = bcryptEncoder;
        this.iUserRolesRepo = iUserRolesRepo;
        this.iRoleRepo = iRoleRepo;
        this.iEmailService = iEmailService;
        this.iRedisRepo = iRedisRepo;
    }

    @PostConstruct
    private void init()
    {
        log.info("Begin Repo");
        var size = this.iRoleRepo.findAll().size();
        log.info("Role size: {}", size);
    }

    @Override
    public List<UserSystemShowDto> getAll()
    {
        List<UserSystem> userList = this.userSystemRepo.findAll();
        Type listType = new TypeToken<List<UserSystemShowDto>>() {}.getType();
        return this.modelMapper.map(userList, listType);
    }

    @Override
    public UserSystemShowDto getById(String username) throws NotFoundException
    {
        Optional<UserSystem> original = this.userSystemRepo.findById(username);
        if (original.isPresent())
        {
            return this.modelMapper.map(original.get(), UserSystemShowDto.class);
        }
        else
        {
            throw new NotFoundException(THE_ID + username + DOESN_T_EXISTS);
        }
    }

    @Override
    public String save(UserSystemDto body)
    {
        UserSystem user = this.modelMapper.map(body, UserSystem.class);
        var passwordHash = bcryptEncoder.encode(user.getPassword());
        log.debug("DTO: {}", body);
        user.setPassword(passwordHash);
        log.debug("Entity: {}", user);
        UserSystem userSaved = this.userSystemRepo.saveAndFlush(user);

        var optionalRole = iRoleRepo.findById(2);
        var role = new Role();
        if (optionalRole.isEmpty())
        {
            log.warn("No role found");
            Role roleNew = new Role();
            roleNew.setId(2);
            roleNew.setName("USER");
            roleNew.setDescription("USER");
            role = iRoleRepo.saveAndFlush(roleNew);
            log.warn("Role: {}", role);

        }
        else
        {
            role = optionalRole.get();
        }
        var userRole = new UserRoles();
        userRole.setUserId(user.getUsername());
        userRole.setRoleId(role.getId());
        log.info("UserRole: {}", userRole);
        iUserRolesRepo.save(userRole);

        return userSaved.getUsername();
    }

    @Override
    public UserSystemDto update(UserSystemDto body, String username) throws NotFoundException
    {
        Optional<UserSystem> original = this.userSystemRepo.findById(username);
        if (original.isPresent())
        {
            UserSystem user = this.modelMapper.map(original.get(), UserSystem.class);
            user = this.userSystemRepo.saveAndFlush(user);
            return this.modelMapper.map(user, UserSystemDto.class);
        }
        else
        {
            throw new NotFoundException(THE_ID + username + DOESN_T_EXISTS);
        }
    }

    @Override
    public Boolean delete(String username) throws NotFoundException
    {
        Optional<UserSystem> original = this.userSystemRepo.findById(username);
        if (original.isPresent())
        {
            this.userSystemRepo.delete(original.get());
            return Boolean.TRUE;
        }
        else
        {
            throw new NotFoundException(THE_ID + username + DOESN_T_EXISTS);
        }
    }

    @Override
    public String forgot(UserSystemDto body) throws NotFoundException
    {
        var user = this.getById(body.getUsername());
        var token = String.valueOf(UUID.randomUUID());
        var redisKey = String.format(redisPropertiesCustom.getKeyUpdate(), user.getUsername());
        log.debug("Redis Key Format: {}", redisKey);
        log.debug("Redis Token: {}", token);
        this.iRedisRepo.saveKey(redisKey, token, redisPropertiesCustom.getTimeUpdate());
        var finalUrl = String.format(mailProperties.getUrlResponse(), user.getUsername(), token);
        var finalBody = String.format(mailProperties.getForgotTemplate(), finalUrl);
        log.debug("Final Body: {}", finalBody);
        this.iEmailService.sendSimpleMessage(user.getEmail(), "Forgot password", finalBody, mailProperties.getFrom());
        return "The email send correct";
    }

    @Override
    public String recover(RecoverPasswordDto body)
    {
        var redisKey = String.format(redisPropertiesCustom.getKeyUpdate(), body.getUsername());
        var token = this.iRedisRepo.getKey(redisKey);
        if (token.equals(body.getToken()))
        {
            Optional<UserSystem> original = this.userSystemRepo.findById(body.getUsername());
            if (original.isPresent())
            {
                var user = original.get();
                var passwordHash = bcryptEncoder.encode(user.getPassword());
                log.debug("DTO: {}", body);
                user.setPassword(passwordHash);
                this.userSystemRepo.saveAndFlush(user);
                this.iRedisRepo.deleteKey(redisKey);
                return "Changed Password";
            }
        }
        return "Incorrect Params";
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException
    {
        log.info("Username: {}", s);
        var optionalUserSystem = userSystemRepo.findById(s);
        if (optionalUserSystem.isEmpty())
        {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        var user = optionalUserSystem.get();
        return new User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserSystem user)
    {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        List<UserRoles> roles = this.iUserRolesRepo.findUserRolesByUserId(user.getUsername());
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleByRoleId().getName())));
        return authorities;
    }
}
