package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.dto.*;
import com.applaudo.studios.moviestore.service.IManageRoleService;
import com.applaudo.studios.moviestore.service.IUserSystemService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    public static final String BODY = "BODY: {}";
    public static final String THE_USER_WITH_ID_S_WAS_UPDATED = "The user with id: %s was updated";

    private final IUserSystemService userSystemService;
    private final IManageRoleService iManageRoleService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseGenericDto<List<UserSystemShowDto>> getAll()
    {
        return new ResponseGenericDto<>(0, "OK", this.userSystemService.getAll());
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseGenericDto<UserSystemShowDto> getById(HttpServletRequest httpServletRequest, @PathVariable("id") String id) throws NotFoundException
    {
        return new ResponseGenericDto<>(0, "OK", this.userSystemService.getById(id));
    }


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseGenericDto<UserSystemDto> update(HttpServletRequest httpServletRequest, @PathVariable("id") String id, @RequestBody @Valid UserSystemDto req) throws NotFoundException
    {
        logger.info(BODY, req);
        String msg = String.format(THE_USER_WITH_ID_S_WAS_UPDATED, id);
        return new ResponseGenericDto<>(0, msg, this.userSystemService.update(req, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/role/{id}")
    public ResponseGenericDto<UserSystemDto> addRole(HttpServletRequest httpServletRequest, @PathVariable("id") String id, @RequestBody @Valid UserRepoReqDto req)
    {
        logger.info(BODY, req);
        String msg = String.format(THE_USER_WITH_ID_S_WAS_UPDATED, id);
        return new ResponseGenericDto<>(0, msg, this.iManageRoleService.addRole(req.getUserDetail(), req.getRoles()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/role/{id}")
    public ResponseGenericDto<UserSystemDto> changeRole(HttpServletRequest httpServletRequest, @PathVariable("id") String id, @RequestBody @Valid UserRepoReqDto req)
    {
        logger.info(BODY, req);
        String msg = String.format(THE_USER_WITH_ID_S_WAS_UPDATED, id);
        return new ResponseGenericDto<>(0, msg, this.iManageRoleService.updateRole(req.getUserDetail(), req.getRoles()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/role/delete/{id}")
    public ResponseGenericDto<UserSystemDto> deleteRole(HttpServletRequest httpServletRequest, @PathVariable("id") String id, @RequestBody @Valid UserRepoReqDto req)
    {
        logger.info(BODY, req);
        String msg = String.format(THE_USER_WITH_ID_S_WAS_UPDATED, id);
        return new ResponseGenericDto<>(0, msg, this.iManageRoleService.deleteRole(req.getUserDetail(), req.getRoles()));
    }

}
