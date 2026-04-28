package com.campusconnect.backend.controller;

import com.campusconnect.backend.dto.LoginRequest;
import com.campusconnect.backend.dto.SignupRequest;
import com.campusconnect.backend.model.User;
import com.campusconnect.backend.service.AuthService;
import com.campusconnect.backend.service.AuditLogService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuditLogService auditLogService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req, HttpSession session) {
        try {
            User user = authService.signup(req, session);
            auditLogService.log("SIGNUP", "User", (long) user.getId(),
                user.getId(), user.getName(), "New user registered: " + user.getUsername());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req, HttpSession session) {
        try {
            User user = authService.login(req, session);
            auditLogService.log("LOGIN", "User", (long) user.getId(),
                user.getId(), user.getName(), "User logged in: " + user.getUsername());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        User user = authService.getCurrentUser(session);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        User user = authService.getCurrentUser(session);
        if (user != null) {
            auditLogService.log("LOGOUT", "User", (long) user.getId(),
                user.getId(), user.getName(), "User logged out");
        }
        authService.logout(session);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }
}
