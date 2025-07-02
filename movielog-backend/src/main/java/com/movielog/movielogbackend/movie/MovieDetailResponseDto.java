package com.movielog.movielogbackend.movie;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MovieDetailResponseDto {

    private final Long id;
    private final String title;
    private final String director;
    private final LocalDate releaseDate;
    private final String genre;
    private final String description;
    private final String posterImageUrl;
    private final double averageRating;

    public MovieDetailResponseDto(Movie movie){
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.director = movie.getDirector();
        this.releaseDate = movie.getReleaseDate();
        this.genre = movie.getGenre();
        this.description = movie.getDescription();
        this.posterImageUrl = movie.getPosterImageUrl();
        this.averageRating = movie.getAverageRating();
    }

}
