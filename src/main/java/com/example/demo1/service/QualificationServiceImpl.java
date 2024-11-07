package com.example.demo1.service;

import com.example.demo1.model.Qualification;
import com.example.demo1.repository.QualificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Mark as a Spring service bean
public class QualificationServiceImpl implements QualificationService {
    private final QualificationRepository qualificationRepository;

    public QualificationServiceImpl(QualificationRepository qualificationRepository) {
        this.qualificationRepository = qualificationRepository;
    }

    @Override
    public void save(Qualification qualification) {
        if (qualification == null || qualification.getDoctorID() <= 0 || qualification.getDegree() == null || qualification.getDegree().isEmpty()) {
            throw new IllegalArgumentException("Invalid qualification data.");
        }
        qualificationRepository.save(qualification);
    }

    @Override
    public List<Qualification> findByDoctorId(int doctorID) {
        if (doctorID <= 0) {
            throw new IllegalArgumentException("Invalid doctor ID.");
        }
        return qualificationRepository.findByDoctorId(doctorID);
    }

    @Override
    public List<Qualification> findAll() {
        return qualificationRepository.findAll();
    }

    @Override
    public void delete(int doctorID, String degree) {
        if (doctorID <= 0 || degree == null || degree.isEmpty()) {
            throw new IllegalArgumentException("Invalid doctor ID or degree.");
        }
        qualificationRepository.delete(doctorID, degree);
    }
}
