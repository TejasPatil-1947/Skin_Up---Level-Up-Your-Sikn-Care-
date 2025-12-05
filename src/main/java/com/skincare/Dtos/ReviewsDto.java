package com.skincare.Dtos;

import com.skincare.Entities.Product;
import lombok.Data;

@Data
public class ReviewsDto {

    private String reviewText;

    private int ratings;

    private Product product;

    private UserDto user;

}
