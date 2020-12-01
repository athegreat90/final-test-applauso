package com.applaudo.studios.moviestore.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Data
public class UserRolesPK implements Serializable
{
    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "role_id", nullable = false)
    private Integer roleId;
}
