package com.applaudo.studios.moviestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "user_movie_like", schema = "accion_finaltest")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserMovieLike
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private UserSystem userSystemByUsername;

    @ManyToOne
    @JoinColumn(name = "id_movie", referencedColumnName = "id", nullable = false)
    private Movie movieByIdMovie;

}
