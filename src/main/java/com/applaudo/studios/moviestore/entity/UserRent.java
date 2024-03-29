package com.applaudo.studios.moviestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_rent", schema = "accion_finaltest")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRent
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "date_begin", nullable = false)
    private Date dateBegin;

    @Basic
    @Column(name = "date_end", nullable = false)
    private Date dateEnd;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private UserSystem userSystemByUsername;

    @ManyToOne
    @JoinColumn(name = "id_movie", referencedColumnName = "id")
    private Movie movieByIdMovie;
}
