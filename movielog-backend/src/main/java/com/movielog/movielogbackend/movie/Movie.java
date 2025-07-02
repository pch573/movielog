package com.movielog.movielogbackend.movie;

import com.movielog.movielogbackend.common.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "movies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends Timestamped { // Timestamped 클래스를 상속받습니다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String director;

    private LocalDate releaseDate;

    private String genre;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String posterImageUrl;

    private double averageRating = 0.0;
}
