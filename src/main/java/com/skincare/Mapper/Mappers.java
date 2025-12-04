package com.skincare.Mapper;

import com.skincare.Dtos.RoutineDto;
import com.skincare.Dtos.UserDto;
import com.skincare.Entities.Routine;
import com.skincare.Entities.User;

public class Mappers {


    public static RoutineDto mapToRoutineDto(Routine routine){
        RoutineDto dto = new RoutineDto();
        dto.setFrequency(routine.getFrequency());
        dto.setProductName(routine.getProductName());
        dto.setRoutineName(routine.getRoutineName());
        dto.setTimeOfDay(routine.getTimeOfDay());
        dto.setStepDescription(routine.getStepDescription());
        dto.setFrequency(routine.getFrequency());

        User user = routine.getUser();
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setGender(user.getGender());
        userDto.setProfileImg(user.getProfileImg());
        userDto.setSkinType(user.getSkinType());

        dto.setUser(userDto);

        return dto;
    }

    public static UserDto mapToUserDto(User user){
        UserDto dto = new UserDto();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setGender(user.getGender());
        dto.setSkinType(user.getSkinType());
        dto.setProfileImg(user.getProfileImg());
        return dto;
    }
}
