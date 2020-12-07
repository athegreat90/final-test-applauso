package com.applaudo.studios.moviestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "movie_picture", schema = "applauso", catalog = "d456p442ibm71f")
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
    @OneToMany(mappedBy = "moviePictureByIdPicture")
    private Collection<MovieXPictures> movieXPicturesById;
}
