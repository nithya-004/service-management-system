package com.service.servicemanagement.service;

import com.service.servicemanagement.entity.Engineer;
import com.service.servicemanagement.repository.EngineerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngineerService {

    @Autowired
    private EngineerRepository engineerRepository;

    public Engineer saveEngineer(Engineer engineer) {
        engineer.setCurrentActiveTickets(0);
        engineer.setPerformanceScore(0.0);
        return engineerRepository.save(engineer);
    }

    public List<Engineer> getAllEngineers() {
        return engineerRepository.findAll();
    }

    public Engineer getEngineerById(Integer id) {
        return engineerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Engineer not found"));
    }
}