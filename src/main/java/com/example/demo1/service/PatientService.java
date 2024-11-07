package com.example.demo1.service;

import com.example.demo1.model.Patient;
import java.util.List;

public interface PatientService {
    Patient findById(int patientID);
    List<Patient> findAll();
    void save(Patient patient);
    void update(Patient patient);
    void delete(int patientID);
}
