package com.service.servicemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.service.servicemanagement.entity.Bill;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {

    long countByStatus(Bill.Status status);

    List<Bill> findByStatus(Bill.Status status);
}