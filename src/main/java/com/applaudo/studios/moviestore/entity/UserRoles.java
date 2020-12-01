package com.applaudo.studios.moviestore.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_roles", schema = "applauso", catalog = "d456p442ibm71f")
@IdClass(UserRolesPK.class)
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserRoles
{
    @Id
    private String userId;
    @Id
    private Integer roleId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
    private UserSystem userSystemByUserId;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Role roleByRoleId;
}
