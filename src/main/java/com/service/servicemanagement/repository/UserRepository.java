package com.service.servicemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.service.servicemanagement.entity.User;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    // ✅ ADD THIS LINE
    long countByRole(String role);
}