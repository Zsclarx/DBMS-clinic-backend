package com.example.demo1.service;

import com.example.demo1.model.Patient;
import com.example.demo1.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient findById(int patientID) {
        try {
            return patientRepository.findById(patientID);
        } catch (SQLException e) {
            e.printStackTrace(); // You can log the exception here
            return null;  // or throw a custom runtime exception
        }
    }

    @Override
    public List<Patient> findAll() {
        try {
            return patientRepository.findAll();
        } catch (SQLException e) {
            e.printStackTrace(); // You can log the exception here
            return null;  // or throw a custom runtime exception
        }
    }

    @Override
    public void save(Patient patient) {
        try {
            // Handle null values at service level
            if (Objects.isNull(patient.getDiseases())) {
                patient.setDiseases("");
            }
            if (Objects.isNull(patient.getEmergencyContactAddress())) {
                patient.setEmergencyContactAddress("");
            }
            if (Objects.isNull(patient.getEmergencyContactPhone())) {
                patient.setEmergencyContactPhone("");
            }
            if (Objects.isNull(patient.getEmergencyContactName())) {
                patient.setEmergencyContactName("");
            }

            patientRepository.save(patient);
        } catch (SQLException e) {
            e.printStackTrace(); // You can log the exception here
        }
    }

    @Override
    public void update(Patient patient) {
        try {
            // Handle null values similarly in update
            if (Objects.isNull(patient.getDiseases())) {
                patient.setDiseases("");
            }
            if (Objects.isNull(patient.getEmergencyContactAddress())) {
                patient.setEmergencyContactAddress("");
            }
            if (Objects.isNull(patient.getEmergencyContactPhone())) {
                patient.setEmergencyContactPhone("");
            }
            if (Objects.isNull(patient.getEmergencyContactName())) {
                patient.setEmergencyContactName("");
            }

            patientRepository.update(patient);
        } catch (SQLException e) {
            e.printStackTrace(); // You can log the exception here
        }
    }

    @Override
    public void delete(int patientID) {
        try {
            patientRepository.delete(patientID);
        } catch (SQLException e) {
            e.printStackTrace(); // You can log the exception here
        }
    }
}
