package com.applaudo.studios.moviestore.service;

import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.applaudo.studios.moviestore.dto.UserSystemShowDto;
import com.applaudo.studios.moviestore.entity.UserRoles;
import com.applaudo.studios.moviestore.entity.UserSystem;
import com.applaudo.studios.moviestore.repository.IRoleRepo;
import com.applaudo.studios.moviestore.repository.IUserRolesRepo;
import com.applaudo.studios.moviestore.repository.IUserSystemRepo;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service(value = "userService")
public class UserSystemServiceImpl implements UserDetailsService, IUserSystemService
{
    private static final Logger logger = LoggerFactory.getLogger(UserSystemServiceImpl.class);

    private final IUserSystemRepo userSystemRepo;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bcryptEncoder;

    private final IUserRolesRepo iUserRolesRepo;

    private final IRoleRepo iRoleRepo;

    public UserSystemServiceImpl(IUserSystemRepo userSystemRepo, ModelMapper modelMapper, BCryptPasswordEncoder bcryptEncoder, IUserRolesRepo iUserRolesRepo, IRoleRepo iRoleRepo) {
        this.userSystemRepo = userSystemRepo;
        this.modelMapper = modelMapper;
        this.bcryptEncoder = bcryptEncoder;
        this.iUserRolesRepo = iUserRolesRepo;
        this.iRoleRepo = iRoleRepo;
    }

    @Override
    public List<UserSystemShowDto> getAll()
    {
        List<UserSystem> userList = this.userSystemRepo.findAll();
        Type listType = new TypeToken<List<UserSystemShowDto>>() {}.getType();
        List<UserSystemShowDto> result = this.modelMapper.map(userList, listType);
        return result;
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
            throw new NotFoundException("The id " + username + " doesn't exists");
        }
    }

    @Override
    public String save(UserSystemDto body)
    {
        UserSystem user = this.modelMapper.map(body, UserSystem.class);
        var passwordHash = bcryptEncoder.encode(user.getPassword());
        logger.info("DTO: {}", body);
        user.setPassword(passwordHash);
        logger.info("Entity: {}", user);
        UserSystem userSaved = this.userSystemRepo.saveAndFlush(user);

        if (userSaved != null)
        {
            var userRole = new UserRoles();
            userRole.setUserId(user.getUsername());
            var role = iRoleRepo.findById(2).get();
//            userRole.setUserSystemByUserId(user);
//            userRole.setRoleByRoleId(role);
            userRole.setRoleId(role.getId());
            iUserRolesRepo.save(userRole);
        }
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
            throw new NotFoundException("The id " + username + " doesn't exists");
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
            throw new NotFoundException("The id " + username + " doesn't exists");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException
    {
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

//        System.out.println("Roles: " + user.getRoles());
        List<UserRoles> roles = this.iUserRolesRepo.findUserRolesByUserId(user.getUsername());
        System.out.println("Roles Consult: " + roles);
        roles.forEach(role ->
        {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleByRoleId().getName()));
        });
        return authorities;
    }
}
