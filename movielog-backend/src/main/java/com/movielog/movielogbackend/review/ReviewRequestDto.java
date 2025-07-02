package com.movielog.movielogbackend.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

    @Min(value = 1, message = "평점은 1 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 5 이하이어야 합니다.")
    private int rating;

    @NotBlank(message = "리뷰 내용은 비워둘 수 없습니다.")
    private String comment;
}
