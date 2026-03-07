package com.service.servicemanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_requests")
public class ServiceRequest {

    public enum Status {
        PENDING,
        COMPLETED
    }

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer requestId;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String description;

    // ✅ NEW FIELD ADDED
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private Integer slaHours;

    private Boolean slaBreached = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime resolvedTime;

    @ManyToOne
    @JoinColumn(name = "engineer_id")
    private Engineer assignedEngineer;

    // ================= GETTERS & SETTERS =================

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ✅ CATEGORY GETTER & SETTER
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Integer getSlaHours() {
        return slaHours;
    }

    public void setSlaHours(Integer slaHours) {
        this.slaHours = slaHours;
    }

    public Boolean getSlaBreached() {
        return slaBreached;
    }

    public void setSlaBreached(Boolean slaBreached) {
        this.slaBreached = slaBreached;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getResolvedTime() {
        return resolvedTime;
    }

    public void setResolvedTime(LocalDateTime resolvedTime) {
        this.resolvedTime = resolvedTime;
    }

    public Engineer getAssignedEngineer() {
        return assignedEngineer;
    }

    public void setAssignedEngineer(Engineer assignedEngineer) {
        this.assignedEngineer = assignedEngineer;
    }
}