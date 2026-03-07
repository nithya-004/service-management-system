package com.service.servicemanagement.controller;

import com.service.servicemanagement.config.JwtUtil;
import com.service.servicemanagement.entity.User;
import com.service.servicemanagement.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // ================= REGISTER =================
    @PostMapping("/register")
    public User register(@RequestBody User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        return userRepository.save(user);
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User loginUser) {

        Optional<User> userOptional = userRepository.findByEmail(loginUser.getEmail());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();

        if (!user.getPassword().equals(loginUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("name", user.getName());
        response.put("role", user.getRole());

        return response;
    }
}