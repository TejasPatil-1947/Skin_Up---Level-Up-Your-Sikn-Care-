package com.skincare.Services.Impl;

import com.skincare.Dtos.ReviewsDto;
import com.skincare.Dtos.UserDto;
import com.skincare.Entities.Product;
import com.skincare.Entities.Reviews;
import com.skincare.Entities.User;
import com.skincare.Mapper.Mappers;
import com.skincare.Repositories.ProductRepository;
import com.skincare.Repositories.ReviewRepository;
import com.skincare.Repositories.UserRepository;
import com.skincare.Services.ProductService;
import com.skincare.Services.ReviewService;
import com.skincare.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ReviewsDto createReview(Long productId, Long userId, Reviews review) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        review.setUser(user);
        review.setProduct(product);

        Reviews savedReview = reviewRepository.save(review);
        product.getReviews().add(savedReview);

        productRepository.save(product);

        return Mappers.mapToReviewDto(savedReview);
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Reviews review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewsDto> findByProductId(Long productId) {
        List<Reviews> byProductId = reviewRepository.findByProductId(productId);
        return byProductId.stream()
                .map(Mappers::mapToReviewDto)
                .toList();
    }

    @Override
    public List<ReviewsDto> findByUserId(Long userId) {
        List<Reviews> byUserId = reviewRepository.findByUserId(userId);
        return byUserId.stream()
                .map(Mappers::mapToReviewDto)
                .toList();
    }
}
