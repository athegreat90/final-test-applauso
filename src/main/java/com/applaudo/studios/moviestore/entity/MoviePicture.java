package com.applaudo.studios.moviestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "movie_picture", schema = "accion_finaltest")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MoviePicture
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "url", nullable = false, length = 300)
    private String url;

    @Basic
    @Column(name = "movie_by_id_movie", nullable = true)
    private Integer movieByIdMovie;

    @ManyToOne
    @JoinColumn(name = "id_movie", referencedColumnName = "id")
    private Movie movieByIdMovie_0;
}
