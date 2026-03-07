package com.service.servicemanagement.controller;

import com.service.servicemanagement.entity.Engineer;
import com.service.servicemanagement.service.EngineerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/engineers")
@CrossOrigin(origins = "http://localhost:3000")
public class EngineerController {

    @Autowired
    private EngineerService engineerService;

    @PostMapping
    public Engineer createEngineer(@RequestBody Engineer engineer) {
        return engineerService.saveEngineer(engineer);
    }

    @GetMapping
    public List<Engineer> getAllEngineers() {
        return engineerService.getAllEngineers();
    }
}