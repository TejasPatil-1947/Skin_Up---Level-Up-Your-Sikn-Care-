package com.skincare.Services;

import com.skincare.Entities.User;
import com.skincare.Request.LoginRequest;
import com.skincare.Response.LoginResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    @Nullable User getUserById(Long id);

    @Nullable User registerUser(User user);

    LoginResponse login(LoginRequest loginRequest);

    ResponseEntity<String> logout();

    List<User> getAllUser();

    void deleteUser(Long id);

    User updateUser(Long id,User user);
}
