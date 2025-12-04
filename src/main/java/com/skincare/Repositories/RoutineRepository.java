package com.skincare.Repositories;

import com.skincare.Entities.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineRepository extends JpaRepository<Routine,Long> {

    List<Routine> findByUserId(Long userId);
}
