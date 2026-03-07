package com.service.servicemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.servicemanagement.entity.ServiceRequest;

public interface ServiceRequestRepository 
        extends JpaRepository<ServiceRequest, Integer> {

    long countByStatus(ServiceRequest.Status status);

    List<ServiceRequest> findByCustomerNameContainingIgnoreCase(String name);

    List<ServiceRequest> findByStatus(ServiceRequest.Status status);
    List<ServiceRequest> findByDescriptionContainingIgnoreCase(String keyword);
}