package com.applaudo.studios.moviestore.service;

import com.applaudo.studios.moviestore.dto.RoleDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import javassist.NotFoundException;

import java.util.List;

public interface IManageRoleService
{
    List<RoleDto> getRoles();

    RoleDto getRole(Integer id) throws NotFoundException;

    RoleDto getRole(String name) throws NotFoundException;

    Integer addRole(RoleDto body);

    RoleDto updateRole(Integer id, RoleDto body) throws NotFoundException;

    Boolean deleteRole(Integer id) throws NotFoundException;

    UserSystemDto addRole(UserSystemDto user, List<RoleDto> roles);

    UserSystemDto updateRole(UserSystemDto user, List<RoleDto> roles);

    UserSystemDto deleteRole(UserSystemDto user, List<RoleDto> roles);
}
