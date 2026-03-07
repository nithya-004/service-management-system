package com.service.servicemanagement.dto;

public class DashboardDTO {

    private long totalRequests;
    private long pendingRequests;
    private long completedRequests;
    private long totalCustomers;
    private double totalRevenue;
    private long unpaidBills;

    public DashboardDTO(long totalRequests,
                        long pendingRequests,
                        long completedRequests,
                        long totalCustomers,
                        double totalRevenue,
                        long unpaidBills) {

        this.totalRequests = totalRequests;
        this.pendingRequests = pendingRequests;
        this.completedRequests = completedRequests;
        this.totalCustomers = totalCustomers;
        this.totalRevenue = totalRevenue;
        this.unpaidBills = unpaidBills;
    }

    public long getTotalRequests() {
        return totalRequests;
    }

    public long getPendingRequests() {
        return pendingRequests;
    }

    public long getCompletedRequests() {
        return completedRequests;
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public long getUnpaidBills() {
        return unpaidBills;
    }
}