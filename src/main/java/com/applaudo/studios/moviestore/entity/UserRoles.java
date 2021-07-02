package com.applaudo.studios.moviestore.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_roles", schema = "accion_finaltest")
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
