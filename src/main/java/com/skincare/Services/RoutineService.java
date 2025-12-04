package com.skincare.Services;

import com.skincare.Dtos.RoutineDto;
import com.skincare.Entities.Routine;

import java.util.List;

public interface RoutineService {

    Routine saveRoutine(Long userId,Routine routine);
    Routine updateRoutine(Long routineId,Routine routine);
    void deleteRoutine(Long routineId,Long userId);
    List<Routine> getAllRoutines();
    List<RoutineDto> findByUserId(Long userId);
}
