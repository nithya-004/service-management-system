package com.service.servicemanagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.servicemanagement.entity.ServiceRequest;
import com.service.servicemanagement.entity.Bill;
import com.service.servicemanagement.entity.Engineer;
import com.service.servicemanagement.repository.ServiceRequestRepository;
import com.service.servicemanagement.repository.BillRepository;
import com.service.servicemanagement.repository.EngineerRepository;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private EngineerRepository engineerRepository;

    // ================= CREATE REQUEST =================
    public ServiceRequest createRequest(ServiceRequest request) {

        request.setStatus(ServiceRequest.Status.PENDING);
        request.setCreatedAt(LocalDateTime.now());

        if (request.getDescription() == null) {
            throw new RuntimeException("Description cannot be null");
        }

        String desc = request.getDescription().toLowerCase();
        // ================= SIMILAR TICKET DETECTION =================

List<ServiceRequest> existingRequests = repository.findAll();
String similarMessage = null;

for(ServiceRequest existing : existingRequests){

    if(existing.getDescription() != null){

        String existingDesc = existing.getDescription().toLowerCase();
        String[] words = desc.split(" ");

        for(String word : words){

            if(word.length() > 3 && existingDesc.contains(word)){

                similarMessage = "Similar ticket found! Ticket ID: "
                        + existing.getRequestId()
                        + " Description: "
                        + existing.getDescription();

                System.out.println(similarMessage);
                break;
            }
        }
    }
}
        // ================= SIMILAR TICKET DETECTION =================

String[] words = desc.split(" ");

for(String word : words){

    if(word.length() > 4){   // ignore small words

        List<ServiceRequest> similar =
                repository.findByDescriptionContainingIgnoreCase(word);

        if(!similar.isEmpty()){
            System.out.println("Similar ticket found for keyword: " + word);

            for(ServiceRequest r : similar){
                System.out.println("Ticket ID: " + r.getRequestId()
                        + " Description: " + r.getDescription());
            }

            break;
        }
    }
}

        // ================= AUTO CATEGORY =================
        if (desc.contains("server") || desc.contains("database")) {
            request.setCategory("Server Issue");
        }
        else if (desc.contains("network") || desc.contains("internet") || desc.contains("wifi")) {
            request.setCategory("Network Issue");
        }
        else if (desc.contains("ui") || desc.contains("frontend") || desc.contains("display")) {
            request.setCategory("Frontend Issue");
        }
        else if (desc.contains("bug") || desc.contains("error") || desc.contains("debug")) {
            request.setCategory("Software Bug");
        }
        else if (desc.contains("printer")) {
            request.setCategory("Printer Issue");
        }
        else {
            request.setCategory("General Issue");
        }

        // ================= AUTO PRIORITY =================
        if (desc.contains("server down") || desc.contains("production")) {
            request.setPriority(ServiceRequest.Priority.CRITICAL);
            request.setSlaHours(4);
        } 
        else if (desc.contains("bug") || desc.contains("error") || desc.contains("debug")) {
            request.setPriority(ServiceRequest.Priority.HIGH);
            request.setSlaHours(8);
        } 
        else if (desc.contains("ui") || desc.contains("frontend")) {
            request.setPriority(ServiceRequest.Priority.MEDIUM);
            request.setSlaHours(24);
        } 
        else {
            request.setPriority(ServiceRequest.Priority.LOW);
            request.setSlaHours(48);
        }

        // ================= AUTO ENGINEER ASSIGNMENT =================
        Engineer bestEngineer = findBestEngineer(request);

        if (bestEngineer != null) {

            request.setAssignedEngineer(bestEngineer);

            Integer active = bestEngineer.getCurrentActiveTickets();
            if (active == null) active = 0;

            bestEngineer.setCurrentActiveTickets(active + 1);
            engineerRepository.save(bestEngineer);
        }
        if(similarMessage != null){
    System.out.println("ALERT: " + similarMessage);
}
        return repository.save(request);
    }

    // ================= ENGINEER SELECTION =================
    private Engineer findBestEngineer(ServiceRequest request) {

        List<Engineer> engineers = engineerRepository.findAll();
        if (engineers.isEmpty()) return null;

        String desc = request.getDescription() != null
                ? request.getDescription().toLowerCase()
                : "";

        List<Engineer> matchingEngineers = new ArrayList<>();

        for (Engineer eng : engineers) {
            if (eng.getSpecialization() != null &&
                desc.contains(eng.getSpecialization().toLowerCase())) {
                matchingEngineers.add(eng);
            }
        }

        if (matchingEngineers.isEmpty()) {
            matchingEngineers = engineers;
        }

        Engineer bestEngineer = null;
        double highestScore = -9999;

        for (Engineer eng : matchingEngineers) {

            int skill = eng.getSkillLevel() != null ? eng.getSkillLevel() : 0;
            int experience = eng.getExperience() != null ? eng.getExperience() : 0;
            double performance = eng.getPerformanceScore() != null ? eng.getPerformanceScore() : 0.0;
            int activeTickets = eng.getCurrentActiveTickets() != null ? eng.getCurrentActiveTickets() : 0;

            double score = (skill * 2) + experience + performance - activeTickets;

            if (score > highestScore) {
                highestScore = score;
                bestEngineer = eng;
            }
        }

        return bestEngineer;
    }

    // ================= GET METHODS =================
    public List<ServiceRequest> getAllRequests() {
        return repository.findAll();
    }

    public List<ServiceRequest> searchByCustomer(String name) {
        return repository.findByCustomerNameContainingIgnoreCase(name);
    }

    public List<ServiceRequest> getByStatus(ServiceRequest.Status status) {
        return repository.findByStatus(status);
    }

    // ================= COMPLETE REQUEST =================
    public ServiceRequest completeRequest(Integer id, Double amount) {

        ServiceRequest request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(ServiceRequest.Status.COMPLETED);
        request.setResolvedTime(LocalDateTime.now());

        // ===== SLA BREACH CHECK =====
        if (request.getCreatedAt() != null && request.getSlaHours() != null) {

            LocalDateTime slaDeadline =
                    request.getCreatedAt().plusHours(request.getSlaHours());

            if (request.getResolvedTime().isAfter(slaDeadline)) {
                request.setSlaBreached(true);
            } else {
                request.setSlaBreached(false);
            }
        }

        // ===== UPDATE ENGINEER ACTIVE TICKETS =====
        Engineer eng = request.getAssignedEngineer();

        if (eng != null) {

            Integer active = eng.getCurrentActiveTickets();
            if (active == null) active = 0;

            if (active > 0) {
                eng.setCurrentActiveTickets(active - 1);
                engineerRepository.save(eng);
            }
        }

        repository.save(request);

        // ===== CREATE BILL =====
        Bill bill = new Bill();

        bill.setCustomerName(request.getCustomerName());
        bill.setAmount(amount);
        bill.setStatus(Bill.Status.UNPAID);
        bill.setCreatedAt(LocalDateTime.now());
        bill.setServiceRequest(request);

        billRepository.save(bill);

        return request;
    }
    // ================= ENGINEER PERFORMANCE =================
public void calculateEngineerPerformance() {

    List<Engineer> engineers = engineerRepository.findAll();
    List<ServiceRequest> requests = repository.findAll();

    for (Engineer eng : engineers) {

        int completedCount = 0;
        int breachedCount = 0;

        for (ServiceRequest req : requests) {

            if (req.getAssignedEngineer() != null &&
                req.getAssignedEngineer().getId().equals(eng.getId())) {

                if (req.getStatus() == ServiceRequest.Status.COMPLETED) {
                    completedCount++;
                }

                if (Boolean.TRUE.equals(req.getSlaBreached())) {
                    breachedCount++;
                }
            }
        }

        double score = (completedCount * 10) - (breachedCount * 5);

        eng.setPerformanceScore(score);
        engineerRepository.save(eng);
    }
}

    // ================= DASHBOARD =================
    public Map<String, Object> getDashboardSummary() {

        List<ServiceRequest> requests = repository.findAll();
        List<Bill> bills = billRepository.findAll();
        List<Engineer> engineers = engineerRepository.findAll();

        int totalRequests = requests.size();
        int pending = 0;
        int completed = 0;

        for (ServiceRequest req : requests) {
            if (req.getStatus() == ServiceRequest.Status.PENDING) {
                pending++;
            } else if (req.getStatus() == ServiceRequest.Status.COMPLETED) {
                completed++;
            }
        }

        double totalRevenue = 0;

        for (Bill bill : bills) {
            if (bill.getAmount() != null &&
                bill.getStatus() == Bill.Status.PAID) {

                totalRevenue += bill.getAmount();
            }
        }

        Engineer topEngineer = null;
        double highestScore = -9999;

        for (Engineer eng : engineers) {
            if (eng.getPerformanceScore() != null &&
                eng.getPerformanceScore() > highestScore) {

                highestScore = eng.getPerformanceScore();
                topEngineer = eng;
            }
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalRequests", totalRequests);
        summary.put("pendingRequests", pending);
        summary.put("completedRequests", completed);
        summary.put("totalRevenue", totalRevenue);
        summary.put("topEngineer", topEngineer != null ? topEngineer.getName() : "N/A");

        return summary;
    }
}