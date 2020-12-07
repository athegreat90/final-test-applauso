package com.applaudo.studios.moviestore.service;

import com.applaudo.studios.moviestore.config.props.RedisPropertiesCustom;
import com.applaudo.studios.moviestore.dto.RecoverPasswordDto;
import com.applaudo.studios.moviestore.dto.RoleDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.applaudo.studios.moviestore.entity.Role;
import com.applaudo.studios.moviestore.repository.IRedisRepo;
import com.applaudo.studios.moviestore.repository.IRoleRepo;
import com.applaudo.studios.moviestore.repository.IUserRolesRepo;
import javassist.NotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class UserSystemServiceImplTest
{
    public static final String USERNAME = "demo5";
    public static final String PASSWORD = "Demo123";
    @Autowired
    private IUserSystemService iUserSystemService;

    @Autowired
    @Qualifier("userService")
    UserDetailsService userDetailsService;

    @Autowired
    IUserRolesRepo iUserRolesRepo;

    @Autowired
    IManageRoleService iManageRoleService;

    @MockBean
    private IRoleRepo iRoleRepo;

    @Autowired
    IRedisRepo iRedisRepo;

    @Autowired
    RedisPropertiesCustom redisPropertiesCustom;

    @Test
    @Order(1)
    void getAll()
    {
        var users = this.iUserSystemService.getAll();
        assertNotNull(users);
    }

    @Test
    @Order(2)
    void getById() throws NotFoundException
    {
        var users = this.iUserSystemService.getById("demo3");
        assertNotNull(users);
    }

    @Test
    @Order(2)
    void getByIdNotFound()
    {
        assertThrows(NotFoundException.class, () -> this.iUserSystemService.getById("demo4"));
    }

    @Test
    @Order(3)
    void save()
    {
        var userDto = new UserSystemDto();
        userDto.setUsername(USERNAME);
        userDto.setEmail("demo5@gmail.com");
        userDto.setName("Demo 5");
        userDto.setPassword(PASSWORD);
        Optional<Role> optionalRole = Optional.of(new Role(2, "", ""));
        Mockito.when(this.iRoleRepo.findById(Mockito.anyInt())).thenReturn(optionalRole);
        assertNotNull(this.iUserSystemService.save(userDto));
    }

    @Test
    @Order(3)
    void saveRoleNotFound()
    {
        var userDto = new UserSystemDto();
        userDto.setUsername(USERNAME);
        userDto.setEmail("demo5@gmail.com");
        userDto.setName("Demo 5");
        userDto.setPassword(PASSWORD);
        Optional<Role> optionalRole = Optional.of(new Role());

        Mockito.when(this.iRoleRepo.findById(Mockito.anyInt())).thenReturn(optionalRole);
        assertThrows(Exception.class, () -> this.iUserSystemService.save(userDto));
    }

    @Test
    @Order(4)
    void update() throws NotFoundException
    {
        var userDto = new UserSystemDto();
        userDto.setUsername(USERNAME);
        userDto.setEmail("demo5@gmail.com");
        userDto.setName("Demo 5");
        userDto.setPassword(PASSWORD);
        var users = this.iUserSystemService.update(userDto, userDto.getUsername());
        assertNotNull(users);
    }

    @Test
    @Order(4)
    void updateNotFound()
    {
        var userDto = new UserSystemDto();
        userDto.setUsername("demo50");
        userDto.setEmail("demo5@gmail.com");
        userDto.setName("Demo 5");
        userDto.setPassword(PASSWORD);
        assertThrows(NotFoundException.class, () -> this.iUserSystemService.update(userDto, userDto.getUsername()));
    }

    @Test
    @Order(5)
    void forgot() throws NotFoundException
    {
        var userDto = new UserSystemDto();
        userDto.setUsername(USERNAME);
        var res = this.iUserSystemService.forgot(userDto);
        assertNotNull(res);
    }

    @Test
    @Order(6)
    void recover()
    {

        var dto = new RecoverPasswordDto();
        dto.setUsername(USERNAME);
        dto.setNewPassword(PASSWORD);

        var redisKey = String.format(redisPropertiesCustom.getKeyUpdate(), dto.getUsername());
        String token = this.iRedisRepo.getKey(redisKey);
        dto.setToken(token);
        var res = this.iUserSystemService.recover(dto);
        assertNotNull(res);
    }

    @Test
    @Order(7)
    void delete() throws NotFoundException
    {
        var userDto = new UserSystemDto();
        userDto.setUsername(USERNAME);

        var role = new RoleDto();
        role.setId(2);
        iManageRoleService.deleteRole(userDto, List.of(role));
        assertNotNull(this.iUserSystemService.delete(userDto.getUsername()));
    }

    @Test
    @Order(8)
    void deleteNotFound() throws Exception
    {
        var userDto = new UserSystemDto();
        userDto.setUsername(USERNAME);
        assertThrows(Exception.class, () -> this.iUserSystemService.delete(userDto.getUsername()));
    }

    @Test
    @Order(9)
    void loadUserByUsername()
    {
        assertNotNull(this.userDetailsService.loadUserByUsername("demo3"));
    }

    @Test
    @Order(7)
    void loadUserByUsernameNotFound()
    {
        var userDto = new UserSystemDto();
        userDto.setUsername(USERNAME);
        assertThrows(UsernameNotFoundException.class, () -> this.userDetailsService.loadUserByUsername("demo30"));
    }
}