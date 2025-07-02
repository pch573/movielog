package com.movielog.movielogbackend.review;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {
    private final Long id;
    private final String nickname;
    private final int rating;
    private final String comment;
    private final LocalDateTime createdAt;

    public ReviewResponseDto(Review review){
        this.id = review.getId();
        this.nickname = review.getUser().getNickname(); // 리뷰 작성자의 닉네임
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();
    }
}
