package com.movielog.movielogbackend.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    //영화 목록 조회 API
    @GetMapping
    public ResponseEntity<Page<MovieListResponseDto>> getMovies(
            @PageableDefault(size = 10, sort = "releaseDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<MovieListResponseDto> movies =movieService.getMovies(pageable);
        return ResponseEntity.ok(movies);
    }

    //영화 상세 조회 API
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDetailResponseDto> getMovie(@PathVariable Long movieId){
        MovieDetailResponseDto movie = movieService.getMovie(movieId);
        return ResponseEntity.ok(movie);
    }
}
