package com.service.servicemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.servicemanagement.dto.DashboardDTO;
import com.service.servicemanagement.entity.Bill;
import com.service.servicemanagement.entity.ServiceRequest;
import com.service.servicemanagement.repository.BillRepository;
import com.service.servicemanagement.repository.ServiceRequestRepository;

@Service
public class DashboardService {

    @Autowired
    private ServiceRequestRepository requestRepository;

    @Autowired
    private BillRepository billRepository;

    public DashboardDTO getDashboardData() {

        long total = requestRepository.count();

        long pending = requestRepository
                .countByStatus(ServiceRequest.Status.PENDING);

        long completed = requestRepository
                .countByStatus(ServiceRequest.Status.COMPLETED);

        long customers = total;

        double revenue = billRepository.findAll()
                .stream()
                .filter(b -> b.getStatus() == Bill.Status.PAID)
                .mapToDouble(Bill::getAmount)
                .sum();

        long unpaid = billRepository
                .countByStatus(Bill.Status.UNPAID);

        return new DashboardDTO(
                total,
                pending,
                completed,
                customers,
                revenue,
                unpaid
        );
    }
}