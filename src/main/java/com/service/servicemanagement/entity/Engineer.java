package com.service.servicemanagement.entity;

import jakarta.persistence.*;

@Entity
public class Engineer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String specialization;

    private Integer experience;

    private Integer skillLevel;

    private Integer currentActiveTickets=0;

    private Double performanceScore=0.0;

    // ----- Getters and Setters -----

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(Integer skillLevel) {
        this.skillLevel = skillLevel;
    }

    public Integer getCurrentActiveTickets() {
        return currentActiveTickets;
    }

    public void setCurrentActiveTickets(Integer currentActiveTickets) {
        this.currentActiveTickets = currentActiveTickets;
    }

    public Double getPerformanceScore() {
        return performanceScore;
    }

    public void setPerformanceScore(Double performanceScore) {
        this.performanceScore = performanceScore;
    }
}