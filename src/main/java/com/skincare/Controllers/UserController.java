package com.skincare.Controllers;

import com.skincare.Dtos.UserDto;
import com.skincare.Entities.User;
import com.skincare.Repositories.UserRepository;
import com.skincare.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@EnableMethodSecurity
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        return new ResponseEntity<>(userRepository.findById(id).get(),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(id,user),HttpStatus.OK);
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<?> getCurrentUser(Authentication authentication){
        if(authentication == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        String email = authentication.getName();
        User user =userRepository.findByEmail(email);

        return ResponseEntity.ok(user);
    }



}
