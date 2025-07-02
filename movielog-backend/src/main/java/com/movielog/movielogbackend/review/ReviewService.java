package com.movielog.movielogbackend.review;

import com.movielog.movielogbackend.movie.Movie;
import com.movielog.movielogbackend.movie.MovieRepository;
import com.movielog.movielogbackend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    //리뷰 작성
    @Transactional
    public void createReview(Long movieId, ReviewRequestDto requestDto, User user) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화를 찾을 수 없습니다."));

        Review review = Review.builder()
                .rating(requestDto.getRating())
                .comment(requestDto.getComment())
                .user(user)
                .movie(movie)
                .build();

        reviewRepository.save(review);
    }

    // 특정 영화의 리뷰 목록 조회
    @Transactional(readOnly = true)
    public Page<ReviewResponseDto> getReviews(Long movieId, Pageable pageable) {
        return reviewRepository.findByMovieId(movieId, pageable).map(ReviewResponseDto::new);
    }

    // 리뷰 수정
    @Transactional
    public void updateReview(Long reviewId, ReviewRequestDto requestDto, User user) {
        Review review = findReview(reviewId);
        validateReviewAuthor(review, user); // 작성자 본인인지 확인
        review.update(requestDto.getRating(), requestDto.getComment());
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(Long reviewId, User user) {
        Review review = findReview(reviewId);
        validateReviewAuthor(review, user); // 작성자 본인인지 확인
        reviewRepository.delete(review);
    }

    // 공통 메서드: ID로 리뷰 찾기
    private Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));
    }

    // 공통 메서드: 리뷰 작성자와 현재 로그인한 사용자가 일치하는지 검증
    private void validateReviewAuthor(Review review, User user) {
        if (!review.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("리뷰에 대한 권한이 없습니다."); // 실제 서비스에서는 더 구체적인 예외 처리 권장
        }
    }
}
