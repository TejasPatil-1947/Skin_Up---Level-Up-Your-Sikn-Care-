package com.skincare.Services.Impl;


import com.skincare.Entities.Role;
import com.skincare.Entities.User;
import com.skincare.Repositories.RoleRepository;
import com.skincare.Repositories.UserRepository;
import com.skincare.Request.LoginRequest;
import com.skincare.Response.LoginResponse;
import com.skincare.Services.UserService;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtService jwtService;


    @Override
    public @Nullable User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " +id));
    }

    @Override
    public @Nullable User   registerUser(User user) {
        String email = user.getEmail();
        if(userRepository.findByEmail(email) != null){
            throw new RuntimeException("email already exists");
        }

        Role userRole = roleRepository.findByName("ROLE_USER").get();

        User user1 = new User();
        user1.setRoles(Set.of(userRole));
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setGender(user.getGender());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setProfileImg("https://cdn-icons-png.flaticon.com/128/1144/1144760.png");
        user1.setSkinType(user.getSkinType());

        return userRepository.save(user1);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(user == null){
            throw new RuntimeException("user not found exception");
        }
        authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

        String jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder()
                .jwtToken(jwtToken)
                .user(user).build();
    }

    public ResponseEntity<String> logout(){
        ResponseCookie cookie = ResponseCookie.from("JWT","")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,cookie.toString()).body("Logged out successfully");
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found with id: " + id));
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long id,User user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        existingUser.setName(user.getName());
        existingUser.setGender(user.getGender());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setEmail(user.getEmail());
        existingUser.setSkinType(user.getSkinType());

        return userRepository.save(existingUser);
    }


}
