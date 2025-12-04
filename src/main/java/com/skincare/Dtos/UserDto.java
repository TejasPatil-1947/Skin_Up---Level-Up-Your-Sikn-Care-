package com.skincare.Dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private String name;
    private String email;
    private String skinType;
    private String gender;
    private String profileImg;
}
