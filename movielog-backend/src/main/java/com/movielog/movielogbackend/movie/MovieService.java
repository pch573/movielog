package com.movielog.movielogbackend.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    // 영화 목록 조회 (페이징 처리)
    @Transactional(readOnly = true)
    public Page<MovieListResponseDto> getMovies(Pageable pageable) {
        // DB에서 Movie 엔티티 페이지를 가져온 후, 각 Movie를 MovieListResponseDto로 변환합니다.
        return movieRepository.findAll(pageable).map(MovieListResponseDto::new);
    }

    //영화 상세 조회
    @Transactional(readOnly = true)
    public MovieDetailResponseDto getMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화를 찾을 수 없습니다. id=" + movieId));
        return new MovieDetailResponseDto(movie);
    }
}
