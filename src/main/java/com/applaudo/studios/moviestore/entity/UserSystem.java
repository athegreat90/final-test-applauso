package com.applaudo.studios.moviestore.entity;

import lombok.*;

import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user_system", schema = "applauso", catalog = "d456p442ibm71f")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserSystem
{
    @Id
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Basic
    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Basic
    @Column(name = "email", nullable = false, length = 200)
    private String email;

    @Basic
    @Column(name = "name", nullable = false, length = 300)
    private String name;

    @OneToMany(mappedBy = "userSystemByUsername")
    private Collection<UserOrder> userOrdersByUsername;

    @OneToMany(mappedBy = "userSystemByUsername")
    private Collection<UserRent> userRentsByUsername;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
    joinColumns =
    {
        @JoinColumn(name = "user_id")
    },
    inverseJoinColumns =
    {
        @JoinColumn(name = "role_id")
    })
    private Set<Role> roles;
}
