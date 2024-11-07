package com.example.demo1.repository;

import com.example.demo1.model.Prescription;

import java.util.List;

public interface PrescriptionRepository {
    void save(Prescription prescription);
    Prescription findById(int prescriptionID);
    List<Prescription> findAll();

    // New method to find prescriptions by patient ID
    List<Prescription> findByPatientId(int patientID);

    void update(Prescription prescription);
    void delete(int prescriptionID);
}