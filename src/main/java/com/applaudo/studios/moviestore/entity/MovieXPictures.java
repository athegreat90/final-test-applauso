package com.applaudo.studios.moviestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "movie_x_pictures", schema = "applauso", catalog = "d456p442ibm71f")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieXPictures
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_movie", referencedColumnName = "id")
    private Movie movieByIdMovie;

    @ManyToOne
    @JoinColumn(name = "id_picture", referencedColumnName = "id", nullable = false)
    private MoviePicture moviePictureByIdPicture;
}
