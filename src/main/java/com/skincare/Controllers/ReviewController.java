package com.skincare.Controllers;

import com.skincare.Dtos.ReviewsDto;
import com.skincare.Entities.Reviews;
import com.skincare.Services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/save/{userId}/{productId}")
    public ResponseEntity<ReviewsDto> saveReview(@RequestBody Reviews reviews, @PathVariable Long productId, @PathVariable Long userId){
        return new ResponseEntity<>(reviewService.createReview(productId,userId,reviews), HttpStatus.CREATED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<ReviewsDto>> findByProductId(@PathVariable Long id){
        return new ResponseEntity<>(reviewService.findByProductId(id),HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ReviewsDto>> findByUserId(@PathVariable Long id){
        return new ResponseEntity<>(reviewService.findByUserId(id),HttpStatus.OK);
    }


    @DeleteMapping("/{reviewId}/{userId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId,@PathVariable Long userId){
        reviewService.deleteReview(reviewId,userId);
        return new ResponseEntity<>("Review deleted successfully..",HttpStatus.OK);
    }
}
