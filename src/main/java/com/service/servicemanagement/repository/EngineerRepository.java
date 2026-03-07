package com.service.servicemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.service.servicemanagement.entity.Engineer;
import java.util.List;

public interface EngineerRepository extends JpaRepository<Engineer, Integer> {

    List<Engineer> findBySpecialization(String specialization);
}