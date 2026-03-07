package com.service.servicemanagement.controller;

import com.service.servicemanagement.service.ServiceRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    @Autowired
    private ServiceRequestService serviceRequestService;

    // ===== DASHBOARD SUMMARY =====
    // Only ADMIN can access this
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Map<String, Object> getDashboardSummary() {
        return serviceRequestService.getDashboardSummary();
    }
}