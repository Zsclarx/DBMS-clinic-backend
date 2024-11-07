package com.example.demo1.service;

import com.example.demo1.model.Prescription;

import java.util.List;

public interface PrescriptionService {
    void save(Prescription prescription);
    Prescription findById(int prescriptionID);
    List<Prescription> findAll();
    void update(Prescription prescription);
    void delete(int prescriptionID);

    // Method to find prescriptions by patient ID
    List<Prescription> findByappointmentId(int patientID);
}