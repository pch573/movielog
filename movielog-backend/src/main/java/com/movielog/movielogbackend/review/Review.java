package com.movielog.movielogbackend.review;

import com.movielog.movielogbackend.common.Timestamped;
import com.movielog.movielogbackend.movie.Movie;
import com.movielog.movielogbackend.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY) //지연 로딩으로 성능 최적화
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Builder
    public Review(int rating, String comment, User user, Movie movie) {
        this.rating = rating;
        this.comment = comment;
        this.user = user;
        this.movie = movie;
    }

    //수정 기능을 위한 메서드
    public void update(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }
}
