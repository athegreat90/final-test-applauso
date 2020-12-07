package com.applaudo.studios.moviestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRepoReqDto
{
    private UserSystemDto userDetail;
    private List<RoleDto> roles;
}
