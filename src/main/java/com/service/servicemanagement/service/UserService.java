package com.service.servicemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.servicemanagement.entity.User;
import com.service.servicemanagement.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    // -------- REGISTER --------
    public User registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        return userRepository.save(user);
    }


    // -------- LOGIN --------
    public Optional<User> loginUser(String email, String password) {

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(password)) {
            return Optional.empty();
        }

        return Optional.of(user);
    }


    // -------- GET BY ID --------
    public Optional<User> getUserById(int id) {

        return userRepository.findById(id);
    }

}