package com.skincare.Response;

import com.skincare.Entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String jwtToken;
    private User user;

}

