package com.applaudo.studios.moviestore.service;

import com.applaudo.studios.moviestore.dto.RoleDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import javassist.NotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(
        MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ManageRoleServiceTest
{
    @Autowired
    private IManageRoleService iManageRoleService;

    private Integer id;

    @Test
    @Order(1)
    void getRoles()
    {
        List<RoleDto> roles = this.iManageRoleService.getRoles();
        assertNotNull(roles);
    }

    @Test
    @Order(2)
    void getRole() throws NotFoundException
    {
        RoleDto role = this.iManageRoleService.getRole(1);
        assertNotNull(role);
    }

    @Test()
    @Order(2)
    void getRoleThrowNotFound() throws NotFoundException
    {
        assertThrows(NotFoundException.class, () -> this.iManageRoleService.getRole(10000));
    }

    @Test()
    @Order(2)
    void getRoleThrowNotFound2() throws NotFoundException
    {
        assertThrows(NotFoundException.class, () -> this.iManageRoleService.getRole("DEMO123"));
    }

    @Test
    @Order(3)
    void addRole()
    {
        var role = new RoleDto();
        role.setName("DEMO");
        role.setDescription("DEMO");
        role.setId(id);
        id = this.iManageRoleService.addRole(role);
        assertNotNull(id);
    }

    @Test
    @Order(4)
    void updateRole() throws NotFoundException
    {
        RoleDto role = this.iManageRoleService.getRole("DEMO");
        role.setName("DEMO");
        role.setDescription("DEMO 2");
        role = this.iManageRoleService.updateRole(role.getId(), role);
        assertNotNull(role);
    }

    @Test()
    @Order(4)
    void updateRoleThrowNotFound() throws NotFoundException
    {
        assertThrows(NotFoundException.class, () -> this.iManageRoleService.updateRole(10000, new RoleDto()));
    }

    @Test
    @Order(5)
    void deleteRole() throws NotFoundException
    {
        RoleDto role = this.iManageRoleService.getRole("DEMO");
        Boolean result = this.iManageRoleService.deleteRole(role.getId());
        assertNotNull(result);
    }

    @Test()
    @Order(5)
    void deleteRoleThrowNotFound()
    {
        assertThrows(NotFoundException.class, () -> this.iManageRoleService.deleteRole(10000));
    }

    @Test
    @Order(6)
    void testAddRole()
    {
        var userSystemDto = new UserSystemDto();
        userSystemDto.setUsername("demo1");
        var role = new RoleDto(1, "", "");
        this.iManageRoleService.addRole(userSystemDto, List.of(role));
        assertTrue(Boolean.TRUE);
    }

    @Test
    @Order(7)
    void testUpdateRole()
    {
        var userSystemDto = new UserSystemDto();
        userSystemDto.setUsername("demo3");
        var role = new RoleDto(1, "", "");
        this.iManageRoleService.updateRole(userSystemDto, List.of(role));
        assertTrue(Boolean.TRUE);
    }

    @Test
    @Order(8)
    void testDeleteRole()
    {
        var userSystemDto = new UserSystemDto();
        userSystemDto.setUsername("demo3");
        var role = new RoleDto(1, "", "");
        this.iManageRoleService.deleteRole(userSystemDto, List.of(role));
        assertTrue(Boolean.TRUE);
    }
}