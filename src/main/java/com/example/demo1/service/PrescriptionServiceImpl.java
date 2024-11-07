package com.example.demo1.service;

import com.example.demo1.model.Prescription;
import com.example.demo1.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public void save(Prescription prescription) {
        if (prescription == null) {
            throw new IllegalArgumentException("Prescription cannot be null");
        }
        prescriptionRepository.save(prescription);
    }

    @Override
    public Prescription findById(int prescriptionID) {
        if (prescriptionID <= 0) {
            throw new IllegalArgumentException("Invalid Prescription ID");
        }
        return prescriptionRepository.findById(prescriptionID);
    }

    @Override
    public List<Prescription> findAll() {
        return prescriptionRepository.findAll();
    }

    @Override
    public void update(Prescription prescription) {
        if (prescription == null || prescription.getPrescriptionID() <= 0) {
            throw new IllegalArgumentException("Prescription or Prescription ID cannot be null or invalid");
        }
        prescriptionRepository.update(prescription);
    }

    @Override
    public void delete(int prescriptionID) {
        if (prescriptionID <= 0) {
            throw new IllegalArgumentException("Invalid Prescription ID");
        }
        prescriptionRepository.delete(prescriptionID);
    }
    @Override
    public List<Prescription> findByappointmentId(int patientID) {
        return prescriptionRepository.findByPatientId(patientID);
    }

}