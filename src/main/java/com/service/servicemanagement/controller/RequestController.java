package com.service.servicemanagement.controller;

import com.service.servicemanagement.entity.ServiceRequest;
import com.service.servicemanagement.service.ServiceRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin
public class RequestController {

    @Autowired
    private ServiceRequestService serviceRequestService;

    // ================= CREATE REQUEST =================
    @PostMapping
    public ServiceRequest createRequest(@RequestBody ServiceRequest request) {
        return serviceRequestService.createRequest(request);
    }

    // ================= GET ALL REQUESTS =================
    @GetMapping
    public List<ServiceRequest> getAllRequests() {
        return serviceRequestService.getAllRequests();
    }

    // ================= COMPLETE REQUEST =================
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @PutMapping("/complete/{id}")
    public ServiceRequest completeRequest(@PathVariable Integer id,
                                          @RequestParam Double amount) {
        return serviceRequestService.completeRequest(id, amount);
    }

    // ================= SEARCH BY CUSTOMER =================
    @GetMapping("/search")
    public List<ServiceRequest> search(@RequestParam String name) {
        return serviceRequestService.searchByCustomer(name);
    }

    // ================= FILTER BY STATUS =================
    @GetMapping("/filter")
    public List<ServiceRequest> filter(@RequestParam String status) {
        return serviceRequestService.getByStatus(
                ServiceRequest.Status.valueOf(status.toUpperCase())
        );
    }

    // ================= CALCULATE ENGINEER PERFORMANCE =================
    @GetMapping("/calculate-performance")
    public String calculatePerformance() {

        serviceRequestService.calculateEngineerPerformance();

        return "Engineer performance calculated successfully";
    }

    // ================= DASHBOARD =================
    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardSummary() {

        return serviceRequestService.getDashboardSummary();
    }
}