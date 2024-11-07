package com.example.demo1.repository;

import com.example.demo1.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

@Repository
public class PatientRepositoryImpl implements PatientRepository {
    private final DataSource dataSource;

    public PatientRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Patient findById(int patientID) throws SQLException {
        String sql = "SELECT * FROM Patients WHERE PatientID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapPatient(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Patient> findAll() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patients";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                patients.add(mapPatient(rs));
            }
        }
        return patients;
    }

    @Override
    public void save(Patient patient) throws SQLException {
        String sql = "INSERT INTO Patients (UserID, Diseases, EmergencyContactAddress, EmergencyContactPhone, EmergencyContactName) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patient.getUserID());
            stmt.setString(2, patient.getDiseases());
            stmt.setString(3, patient.getEmergencyContactAddress());
            stmt.setString(4, patient.getEmergencyContactPhone());
            stmt.setString(5, patient.getEmergencyContactName());
            stmt.executeUpdate();
        }
    }


    @Override
    public void update(Patient patient) throws SQLException {
        String sql = "UPDATE Patients SET UserID = ?, Diseases = ?, EmergencyContactAddress = ?, EmergencyContactPhone = ?, EmergencyContactName = ? WHERE PatientID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patient.getUserID());
            stmt.setString(2, patient.getDiseases());
            stmt.setString(3, patient.getEmergencyContactAddress());
            stmt.setString(4, patient.getEmergencyContactPhone());
            stmt.setString(5, patient.getEmergencyContactName());
            stmt.setInt(6, patient.getPatientID());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int patientID) throws SQLException {
        String sql = "DELETE FROM Patients WHERE PatientID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientID);
            stmt.executeUpdate();
        }
    }


    private Patient mapPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setPatientID(rs.getInt("PatientID"));
        patient.setUserID(rs.getInt("UserID"));
        patient.setDiseases(rs.getString("Diseases"));
        patient.setEmergencyContactAddress(rs.getString("EmergencyContactAddress"));
        patient.setEmergencyContactPhone(rs.getString("EmergencyContactPhone"));
        patient.setEmergencyContactName(rs.getString("EmergencyContactName"));
        return patient;
    }
}
