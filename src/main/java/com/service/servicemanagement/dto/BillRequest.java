package com.service.servicemanagement.dto;

public class BillRequest {

    private Integer requestId;
    private Double amount;

    // Getters & Setters
    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}