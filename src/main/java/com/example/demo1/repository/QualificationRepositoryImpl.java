package com.example.demo1.repository;

import com.example.demo1.model.Qualification;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository // Mark as a Spring repository bean
public class QualificationRepositoryImpl implements QualificationRepository {
    private final DataSource dataSource;

    public QualificationRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Qualification qualification) {
        String sql = "INSERT INTO qualification (doctorID, degree) VALUES (?, ?)"; // Corrected table name
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, qualification.getDoctorID());
            stmt.setString(2, qualification.getDegree());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Qualification> findByDoctorId(int doctorID) {
        List<Qualification> qualifications = new ArrayList<>();
        String sql = "SELECT doctorID, degree FROM qualification WHERE doctorID = ?"; // Corrected table name
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    qualifications.add(mapQualification(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qualifications;
    }

    @Override
    public List<Qualification> findAll() {
        List<Qualification> qualifications = new ArrayList<>();
        String sql = "SELECT * FROM qualification"; // Corrected table name
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                qualifications.add(mapQualification(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qualifications;
    }

    @Override
    public void delete(int doctorID, String degree) {
        String sql = "DELETE FROM qualification WHERE doctorID = ? AND degree = ?"; // Corrected table name
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorID);
            stmt.setString(2, degree);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Qualification mapQualification(ResultSet rs) throws SQLException {
        Qualification qualification = new Qualification();
        qualification.setDoctorID(rs.getInt("doctorID"));
        qualification.setDegree(rs.getString("degree"));
        return qualification;
    }
}
