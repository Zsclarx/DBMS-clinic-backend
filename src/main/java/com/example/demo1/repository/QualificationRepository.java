package com.example.demo1.repository;

import com.example.demo1.model.Qualification;

import java.util.List;

public interface QualificationRepository {
    void save(Qualification qualification);
    List<Qualification> findByDoctorId(int doctorID); // Changed to return List
    List<Qualification> findAll();
    void delete(int doctorID, String degree); // Accepts degree for deletion
}
