package com.movielog.movielogbackend.review;

import com.movielog.movielogbackend.jwt.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 특정 영화에 리뷰 작성 API
    @PostMapping("/movies/{movieId}/reviews")
    public ResponseEntity<String> createReview(@PathVariable Long movieId,
                                               @Valid @RequestBody ReviewRequestDto requestDto,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewService.createReview(movieId, requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body("리뷰가 성공적으로 작성되었습니다.");
    }

    // 특정 영화의 리뷰 목록 조회 API
    @GetMapping("/movies/{movieId}/reviews")
    public ResponseEntity<Page<ReviewResponseDto>> getReviews(@PathVariable Long movieId,
                                                              @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ReviewResponseDto> reviews = reviewService.getReviews(movieId, pageable);
        return ResponseEntity.ok(reviews);
    }

    // 리뷰 수정 API
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId,
                                               @Valid @RequestBody ReviewRequestDto requestDto,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewService.updateReview(reviewId, requestDto, userDetails.getUser());
        return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
    }

    // 리뷰 삭제 API
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewService.deleteReview(reviewId, userDetails.getUser());
        return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
    }
}
