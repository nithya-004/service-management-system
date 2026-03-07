package com.service.servicemanagement.controller;

import com.service.servicemanagement.entity.Bill;
import com.service.servicemanagement.service.BillService;
import com.service.servicemanagement.repository.BillRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin(origins = "http://localhost:3000")
public class BillController {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillService billService;

    // ================= GET ALL BILLS =================
    @GetMapping
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    // ================= FILTER BY STATUS =================
    @GetMapping("/filter")
    public List<Bill> filterBills(@RequestParam String status) {

        try {
            Bill.Status billStatus = Bill.Status.valueOf(status.toUpperCase());
            return billService.getByStatus(billStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value");
        }
    }

    // ================= GENERATE BILL =================
    @PostMapping("/generate/{requestId}")
    public Bill generateBill(@PathVariable Integer requestId,
                             @RequestParam Double amount) {
        return billService.generateBill(requestId, amount);
    }

    // ================= MARK AS PAID =================
    @PreAuthorize("hasRole('CUSTOMER')")
@PutMapping("/pay/{id}")
public Bill payBill(@PathVariable Integer id) {
    return billService.payBill(id);
}
}