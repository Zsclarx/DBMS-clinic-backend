package com.example.demo1.repository;

import com.example.demo1.model.MedicationDetail;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicationDetailRepositoryImpl implements MedicationDetailRepository {
    private final DataSource dataSource;

    public MedicationDetailRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(MedicationDetail medicationDetail) throws SQLException {
        String sql = "INSERT INTO medication_detail (PrescriptionID, Advice) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, medicationDetail.getPrescriptionID());
            stmt.setString(2, medicationDetail.getAdvice());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(MedicationDetail medicationDetail) throws SQLException {
        String sql = "UPDATE medication_detail SET Advice = ? WHERE PrescriptionID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, medicationDetail.getAdvice());
            stmt.setInt(2, medicationDetail.getPrescriptionID());
            stmt.executeUpdate();
        }
    }


    @Override
    public List<MedicationDetail> findByPrescriptionId(int prescriptionID) throws SQLException {
        List<MedicationDetail> medicationDetails = new ArrayList<>();
        String sql = "SELECT PrescriptionID, Advice FROM medication_detail WHERE PrescriptionID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, prescriptionID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    medicationDetails.add(mapMedicationDetail(rs));
                }
            }
        }
        return medicationDetails;
    }

    @Override
    public List<MedicationDetail> findAll() throws SQLException {
        List<MedicationDetail> medicationDetails = new ArrayList<>();
        String sql = "SELECT * FROM medication_detail";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                medicationDetails.add(mapMedicationDetail(rs));
            }
        }
        return medicationDetails;
    }

    @Override
    public void delete(int prescriptionID) {
        String sql = "DELETE FROM medication_detail WHERE PrescriptionID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, prescriptionID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private MedicationDetail mapMedicationDetail(ResultSet rs) throws SQLException {
        MedicationDetail medicationDetail = new MedicationDetail();
        medicationDetail.setPrescriptionID(rs.getInt("PrescriptionID"));
        medicationDetail.setAdvice(rs.getString("Advice"));
        return medicationDetail;
    }
}
