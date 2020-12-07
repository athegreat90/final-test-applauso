package com.applaudo.studios.moviestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "role", schema = "applauso", catalog = "d456p442ibm71f")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "description", nullable = true, length = 200)
    private String description;
    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;
}
