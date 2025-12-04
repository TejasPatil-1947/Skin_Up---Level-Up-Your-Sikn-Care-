package com.skincare.Controllers;

import com.skincare.Entities.Routine;
import com.skincare.Services.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routine")
public class RoutineController {

    @Autowired
    private RoutineService routineService;

    @PostMapping("/save/{userId}")
    public ResponseEntity<Routine> saveRoutine(@PathVariable Long userId, @RequestBody Routine routine){
        return new ResponseEntity<>(routineService.saveRoutine(userId,routine), HttpStatus.CREATED);
    }

    @GetMapping("/getRoutines/{userId}")
    public ResponseEntity<List<Routine>> getAllRoutinesByUserId(@PathVariable Long userId){
        return new ResponseEntity<>(routineService.getAllRoutines(),HttpStatus.OK);
    }

    @PutMapping("/update/{routineId}")
    public ResponseEntity<Routine> updateRoutine(@PathVariable Long routineId,@RequestBody Routine routine){
        return new ResponseEntity<>(routineService.updateRoutine(routineId,routine),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}/{routineId}")
    public ResponseEntity<?> deleteRoutine(@PathVariable Long userId,@PathVariable Long routineId){
        routineService.deleteRoutine(routineId,userId);
        return new ResponseEntity<>("Routine deleted successfully",HttpStatus.OK);
    }
}
