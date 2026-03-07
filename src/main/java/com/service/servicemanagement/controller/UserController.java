package com.service.servicemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.service.servicemanagement.entity.User;
import com.service.servicemanagement.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // ---------------- REGISTER USER ----------------
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {

        User savedUser = userService.registerUser(user);

        return ResponseEntity.ok(savedUser);
    }


    // ---------------- LOGIN USER ----------------
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {

        Optional<User> user = userService.loginUser(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid Email or Password");
        }
    }


    // ---------------- GET USER BY ID ----------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {

        Optional<User> user = userService.getUserById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

}