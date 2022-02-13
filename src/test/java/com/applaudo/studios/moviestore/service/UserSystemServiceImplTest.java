package com.applaudo.studios.moviestore.service;

import com.applaudo.studios.moviestore.config.TestRedisConfiguration;
import com.applaudo.studios.moviestore.config.props.RedisPropertiesCustom;
import com.applaudo.studios.moviestore.dto.RecoverPasswordDto;
import com.applaudo.studios.moviestore.dto.RoleDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.applaudo.studios.moviestore.entity.Role;
import com.applaudo.studios.moviestore.entity.UserSystem;
import com.applaudo.studios.moviestore.repository.IRedisRepo;
import com.applaudo.studios.moviestore.repository.IRoleRepo;
import com.applaudo.studios.moviestore.repository.IUserRolesRepo;
import com.applaudo.studios.moviestore.repository.IUserSystemRepo;
import com.applaudo.studios.moviestore.service.rest.IManageRoleService;
import com.applaudo.studios.moviestore.service.rest.IUserSystemService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import redis.embedded.RedisServer;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@Slf4j
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

    @SpyBean
    private RedisTemplate<String, Object> redisTemplate;

    @MockBean
    private ValueOperations valueOperations;

    @SpyBean
    private JavaMailSender emailSender;

    private RedisServer redisServer;

    @BeforeEach
    public void setUp()
    {
        try {
            redisServer = RedisServer.builder().port(6370).build();
            redisServer.start();
        } catch (Exception e)
        {
            //
        }
    }

    @Test
    @Order(3)
    void getAll()
    {
        var users = this.iUserSystemService.getAll();
        log.info("{}", users);
        assertNotNull(users);
    }

    @Test
    @Order(2)
    void getById() throws NotFoundException
    {
        var users = this.iUserSystemService.getById(USERNAME);
        assertNotNull(users);
    }

    @Test
    @Order(2)
    void getByIdNotFound()
    {
        assertThrows(NotFoundException.class, () -> this.iUserSystemService.getById("demo4"));
    }

    @Test
    @Order(1)
    void save()
    {
        UserSystemDto userDto = getUser(new Role(2, "", ""));
        assertNotNull(this.iUserSystemService.save(userDto));
    }

    @Test
    @Order(3)
    void saveRoleNotFound()
    {
        UserSystemDto userDto = getUser(new Role());
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
        doReturn(valueOperations).when(redisTemplate).opsForValue();

        doNothing().when(valueOperations).set(anyString(), any());
        doReturn(Boolean.TRUE).when(redisTemplate).expire(anyString(), anyLong(), any());

        doNothing().when(emailSender).send(any(SimpleMailMessage.class));

        var userDto = new UserSystemDto();
        userDto.setUsername(USERNAME);
        var res = this.iUserSystemService.forgot(userDto);
        assertNotNull(res);
    }

    @Test
    @Order(6)
    void recover()
    {
        var token = "608153ea-3ac0-4cd1-a856-68a332bcaa76";
        var dto = new RecoverPasswordDto();
        dto.setUsername(USERNAME);
        dto.setNewPassword(PASSWORD);

        doReturn(valueOperations).when(redisTemplate).opsForValue();
        doReturn(token).when(valueOperations).get(anyString());
        doReturn(Boolean.TRUE).when(redisTemplate).delete(anyString());

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
        UserSystemDto userDto = getUser(new Role(2, "", ""));
        this.iUserSystemService.save(userDto);
        assertNotNull(this.userDetailsService.loadUserByUsername(USERNAME));
    }

    @NotNull
    private UserSystemDto getUser(Role value) {
        var userDto = new UserSystemDto();
        userDto.setUsername(USERNAME);
        userDto.setEmail("demo5@gmail.com");
        userDto.setName("Demo 5");
        userDto.setPassword(PASSWORD);
        Optional<Role> optionalRole = Optional.of(value);
        when(this.iRoleRepo.findById(Mockito.anyInt())).thenReturn(optionalRole);
        return userDto;
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