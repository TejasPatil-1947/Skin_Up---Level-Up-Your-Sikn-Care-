package com.skincare.Services;

import com.skincare.Dtos.ReviewsDto;
import com.skincare.Entities.Reviews;

import java.util.List;

public interface ReviewService {

    ReviewsDto createReview(Long productId, Long userId, Reviews review);

    void deleteReview(Long reviewId,Long userId);

    List<ReviewsDto> findByProductId(Long productId);

    List<ReviewsDto> findByUserId(Long userId);
}
