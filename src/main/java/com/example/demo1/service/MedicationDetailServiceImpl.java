package com.example.demo1.service;

import com.example.demo1.model.MedicationDetail;
import com.example.demo1.repository.MedicationDetailRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MedicationDetailServiceImpl implements MedicationDetailService {
    private final MedicationDetailRepository medicationDetailRepository;

    public MedicationDetailServiceImpl(MedicationDetailRepository medicationDetailRepository) {
        this.medicationDetailRepository = medicationDetailRepository;
    }

    @Override
    public void save(MedicationDetail medicationDetail) throws SQLException {
        medicationDetailRepository.save(medicationDetail);
    }

    @Override
    public List<MedicationDetail> findByPrescriptionId(int prescriptionID) throws SQLException {
        return medicationDetailRepository.findByPrescriptionId(prescriptionID);
    }

    @Override
    public List<MedicationDetail> findAll() throws SQLException {
        return medicationDetailRepository.findAll();
    }

    @Override
    public void update(MedicationDetail medicationDetail) throws SQLException {
        medicationDetailRepository.update(medicationDetail); // Make sure the repository has this method
    }

    @Override
    public void delete(int prescriptionID) {
        medicationDetailRepository.delete(prescriptionID);
    }
}
