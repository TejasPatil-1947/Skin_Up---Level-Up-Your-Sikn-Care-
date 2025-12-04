package com.skincare.Services.Impl;

import com.skincare.Dtos.RoutineDto;
import com.skincare.Entities.Routine;
import com.skincare.Entities.User;
import com.skincare.Mapper.Mappers;
import com.skincare.Repositories.RoutineRepository;
import com.skincare.Repositories.UserRepository;
import com.skincare.Services.RoutineService;
import com.skincare.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineServiceImpl implements RoutineService {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public Routine saveRoutine(Long userId, Routine routine) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        routine.setUser(user);
        return routineRepository.save(routine);
    }

    @Override
    public Routine updateRoutine(Long routineId, Routine routine) {
        Routine exRoutine = routineRepository.findById(routineId).orElseThrow(() -> new RuntimeException("Routine not found with id: " + routineId));
        exRoutine.setRoutineName(routine.getRoutineName());
        exRoutine.setFrequency(routine.getFrequency());
        exRoutine.setProductName(routine.getProductName());
        exRoutine.setStepDescription(routine.getStepDescription());
        exRoutine.setTimeOfDay(routine.getTimeOfDay());
        return routineRepository.save(exRoutine);
    }

    @Override
    public void deleteRoutine(Long routineId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found with id: " + userId));
        Routine routine = routineRepository.findById(routineId).orElseThrow(() -> new RuntimeException("Routine not found with id: " + routineId));
        routineRepository.delete(routine);
    }

    @Override
    public List<Routine> getAllRoutines() {
        return routineRepository.findAll();
    }

    @Override
    public List<RoutineDto> findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found with id: " + userId));
        List<Routine> byUserId = routineRepository.findByUserId(userId);
       return byUserId.stream()
                .map(Mappers::mapToRoutineDto)
                .toList();
    }
}
