package com.example.demo1.repository;

import com.example.demo1.model.Patient;

import java.sql.SQLException;
import java.util.List;

public interface PatientRepository {
    Patient findById(int patientID) throws SQLException;
    List<Patient> findAll() throws SQLException;
    void save(Patient patient) throws SQLException;
    void update(Patient patient) throws SQLException;
    void delete(int patientID) throws SQLException;
}
