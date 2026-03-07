package com.service.servicemanagement.service;

import com.service.servicemanagement.entity.Bill;
import com.service.servicemanagement.entity.ServiceRequest;
import com.service.servicemanagement.repository.BillRepository;
import com.service.servicemanagement.repository.ServiceRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    // ================= GENERATE BILL =================
    public Bill generateBill(Integer requestId, Double amount) {

        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        Bill bill = new Bill();
        bill.setAmount(amount);
        bill.setStatus(Bill.Status.UNPAID);
        bill.setCreatedAt(LocalDateTime.now());

        // If Bill has customerName field
        bill.setCustomerName(request.getCustomerName());

        return billRepository.save(bill);
    }

    // ================= PAY BILL =================
    public Bill payBill(Integer id) {

        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        bill.setStatus(Bill.Status.PAID);

        return billRepository.save(bill);
    }

    // ================= FILTER BY STATUS =================
    public List<Bill> getByStatus(Bill.Status status) {
        return billRepository.findByStatus(status);
    }
}