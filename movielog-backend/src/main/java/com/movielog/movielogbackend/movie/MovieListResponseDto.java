package com.movielog.movielogbackend.movie;

import lombok.Getter;

@Getter
public class MovieListResponseDto {
    private final Long id;
    private final String title;
    private final String posterImageUrl;
    private final double averageRating;

    public MovieListResponseDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.posterImageUrl = movie.getPosterImageUrl();
        this.averageRating = movie.getAverageRating();
    }
}
