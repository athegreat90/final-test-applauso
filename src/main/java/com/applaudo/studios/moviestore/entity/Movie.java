package com.applaudo.studios.moviestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "movie", schema = "accion_finaltest")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movie
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    @Basic
    @Column(name = "description", nullable = true, length = 2000)
    private String description;
    @Basic
    @Column(name = "stock", nullable = false)
    private Integer stock;
    @Basic
    @Column(name = "rental_price", nullable = false, precision = 0)
    private Double rentalPrice;
    @Basic
    @Column(name = "sale_price", nullable = false, precision = 0)
    private Double salePrice;
    @Basic
    @Column(name = "availability", nullable = true)
    private Boolean availability;

    @OneToMany(mappedBy = "movieByIdMovie_0")
    private Collection<MoviePicture> moviePicturesById;

    @OneToMany(mappedBy = "movieByIdMovie", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<UserRent> userRentsById;
}
