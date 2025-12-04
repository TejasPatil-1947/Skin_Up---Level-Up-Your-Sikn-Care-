package com.skincare.Dtos;

import lombok.Data;

@Data
public class RoutineDto {


    private String routineName;
    private String productName;
    private String stepDescription;
    private String frequency;
    private String timeOfDay;

    private UserDto user;
}
