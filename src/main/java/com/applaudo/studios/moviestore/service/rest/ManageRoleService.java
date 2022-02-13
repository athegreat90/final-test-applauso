package com.applaudo.studios.moviestore.service.rest;

import com.applaudo.studios.moviestore.dto.MovieDto;
import com.applaudo.studios.moviestore.dto.RoleDto;
import com.applaudo.studios.moviestore.dto.UserSystemDto;
import com.applaudo.studios.moviestore.entity.Role;
import com.applaudo.studios.moviestore.entity.UserRoles;
import com.applaudo.studios.moviestore.entity.UserRolesPK;
import com.applaudo.studios.moviestore.entity.UserSystem;
import com.applaudo.studios.moviestore.repository.IRoleRepo;
import com.applaudo.studios.moviestore.repository.IUserRolesRepo;
import com.applaudo.studios.moviestore.repository.IUserSystemRepo;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ManageRoleService implements IManageRoleService
{
    public static final String ROLE_NOT_FOUND = "Role not found";
    private final IUserRolesRepo iUserRolesRepo;
    private final IUserSystemRepo iUserSystemRepo;
    private final IRoleRepo iRoleRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<RoleDto> getRoles()
    {
        var roles = this.iRoleRepo.findAll();
        Type listType = new TypeToken<List<MovieDto>>() {}.getType();
        return this.modelMapper.map(roles, listType);
    }

    @Override
    public RoleDto getRole(Integer id) throws NotFoundException
    {
        var optionRole = this.iRoleRepo.findById(id);
        if (optionRole.isEmpty())
        {
            throw new NotFoundException(ROLE_NOT_FOUND);
        }
        return this.modelMapper.map(optionRole.get(), RoleDto.class);
    }

    @Override
    public RoleDto getRole(String name) throws NotFoundException
    {
        var optionRole = this.iRoleRepo.findRoleByName(name);
        if (optionRole == null)
        {
            throw new NotFoundException(ROLE_NOT_FOUND);
        }
        return this.modelMapper.map(optionRole, RoleDto.class);
    }

    @Override
    public Integer addRole(RoleDto body)
    {
        Role role = this.modelMapper.map(body, Role.class);
        log.info("Dto: {}",body);
        log.info("Entity: {}", role);
        role = this.iRoleRepo.save(role);
        return role.getId();
    }

    @Override
    public RoleDto updateRole(Integer id, RoleDto body) throws NotFoundException
    {
        var optionRole = this.iRoleRepo.findById(id);
        if (optionRole.isEmpty())
        {
            throw new NotFoundException(ROLE_NOT_FOUND);
        }
        body.setId(id);
        Role role = this.modelMapper.map(body, Role.class);
        this.iRoleRepo.saveAndFlush(role);
        return body;
    }

    @Override
    public Boolean deleteRole(Integer id) throws NotFoundException
    {
        var optionRole = this.iRoleRepo.findById(id);
        if (optionRole.isEmpty())
        {
            throw new NotFoundException(ROLE_NOT_FOUND);
        }
        this.iRoleRepo.delete(optionRole.get());
        return Boolean.TRUE;
    }

    @Override
    public UserSystemDto addRole(UserSystemDto user, List<RoleDto> roles)
    {
        var userOriginal = this.modelMapper.map(user, UserSystem.class);
        Type listType = new TypeToken<List<Role>>() {}.getType();
        addRole(user, roles, userOriginal, listType);
        return user;
    }

    @Override
    public UserSystemDto updateRole(UserSystemDto user, List<RoleDto> roles)
    {
        var userOriginal = this.modelMapper.map(user, UserSystem.class);
        Type listType = new TypeToken<List<Role>>() {}.getType();

        var rolesByUser = this.iUserRolesRepo.findUserRolesByUserId(user.getUsername());
        this.iUserRolesRepo.deleteAll(rolesByUser);

        addRole(user, roles, userOriginal, listType);
        return user;
    }

    private void addRole(UserSystemDto user, List<RoleDto> roles, UserSystem userOriginal, Type listType)
    {
        List<Role> rolesOriginals = this.modelMapper.map(roles, listType);
        rolesOriginals.forEach(role ->
        {
            UserRolesPK pk = new UserRolesPK();
            pk.setRoleId(role.getId());
            pk.setUserId(user.getUsername());
            UserRoles userRoles = new UserRoles();
            userRoles.setUserSystemByUserId(userOriginal);
            userRoles.setRoleByRoleId(role);
            userRoles.setRoleId(role.getId());
            userRoles.setUserId(user.getUsername());
            this.iUserRolesRepo.saveAndFlush(userRoles);
        });
    }

    @Override
    public UserSystemDto deleteRole(UserSystemDto user, List<RoleDto> roles)
    {
        var rolesToDelete = roles.stream().map(roleDto ->
        {
            var pk = new UserRoles();
            pk.setUserId(user.getUsername());
            pk.setRoleId(roleDto.getId());
            return pk;
        }).collect(Collectors.toList());
        this.iUserRolesRepo.deleteAll(rolesToDelete);
        return user;
    }
}
