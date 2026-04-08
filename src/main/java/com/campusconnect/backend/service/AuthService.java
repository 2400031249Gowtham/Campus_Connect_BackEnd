package com.campusconnect.backend.service;

import com.campusconnect.backend.dto.LoginRequest;
import com.campusconnect.backend.dto.SignupRequest;
import com.campusconnect.backend.model.User;
import com.campusconnect.backend.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public User signup(SignupRequest req, HttpSession session) {
        if (userRepo.findByUsernameIgnoreCase(req.getUsername()).isPresent())
            throw new RuntimeException("Username already taken");

        User user = new User();
        user.setUsername(req.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setName(req.getName());
        user.setRole(User.Role.valueOf(req.getRole()));

        User saved = userRepo.save(user);
        session.setAttribute("userId", saved.getId());
        return saved;
    }

    public User login(LoginRequest req, HttpSession session) {
        User user = userRepo.findByUsernameIgnoreCase(req.getUsername())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        session.setAttribute("userId", user.getId());
        return user;
    }

    public User getCurrentUser(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return null;
        return userRepo.findById(userId).orElse(null);
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public java.util.List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
