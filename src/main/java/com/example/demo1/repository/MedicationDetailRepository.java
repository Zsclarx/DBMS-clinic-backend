package com.example.demo1.repository;

import com.example.demo1.model.MedicationDetail;

import java.sql.SQLException;
import java.util.List;

public interface MedicationDetailRepository {
    void save(MedicationDetail medicationDetail) throws SQLException;

    void update(MedicationDetail medicationDetail) throws SQLException;

    List<MedicationDetail> findByPrescriptionId(int prescriptionID) throws SQLException;
    List<MedicationDetail> findAll() throws SQLException;
    void delete(int prescriptionID);
}
