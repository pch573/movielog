package com.movielog.movielogbackend.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 특정 영화의 리뷰들을 페이징 처리하여 조회
    Page<Review> findByMovieId(Long movieId, Pageable pageable);
}
