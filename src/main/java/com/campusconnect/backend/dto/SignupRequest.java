package com.campusconnect.backend.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String name;
    private String role;
}
