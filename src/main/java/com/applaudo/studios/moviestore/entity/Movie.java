package com.applaudo.studios.moviestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "movie", schema = "applauso", catalog = "d456p442ibm71f")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "movieByIdMovie")
    private Collection<UserRent> userRentsById;
}
