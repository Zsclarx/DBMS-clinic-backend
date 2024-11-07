package com.example.demo1.repository;

import com.example.demo1.model.Doctor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DoctorRepositoryImpl implements DoctorRepository {

    private final JdbcTemplate jdbcTemplate;

    public DoctorRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null");
        }

        String sql = "INSERT INTO Doctors (userID, specialization, departmentID, fees, rating) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, doctor.getUserID(), doctor.getSpecialization(),
                doctor.getDepartmentID(), doctor.getFees(), doctor.getRating());
    }

    @Override
    public int update(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null");
        }

        if (doctor.getDoctorID() <= 0) {
            throw new IllegalArgumentException("Doctor ID must be positive");
        }

        String sql = "UPDATE Doctors SET userID=?, specialization=?, departmentID=?, fees=?, rating=? WHERE doctorID=?";
        return jdbcTemplate.update(sql, doctor.getUserID(), doctor.getSpecialization(),
                doctor.getDepartmentID(), doctor.getFees(), doctor.getRating(), doctor.getDoctorID());
    }

    @Override
    public Doctor findById(int doctorID) {
        if (doctorID <= 0) {
            throw new IllegalArgumentException("Doctor ID must be positive");
        }

        String sql = "SELECT * FROM Doctors WHERE doctorID = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToDoctor, doctorID);
    }

    @Override
    public List<Doctor> findAll() {
        String sql = "SELECT * FROM Doctors";
        return jdbcTemplate.query(sql, this::mapRowToDoctor);
    }

    @Override
    public List<Doctor> findByDepartmentId(int departmentID) {
        if (departmentID <= 0) {
            throw new IllegalArgumentException("Department ID must be positive");
        }

        String sql = "SELECT * FROM Doctors WHERE departmentID = ? ORDER BY doctorID ASC";
        return jdbcTemplate.query(sql, this::mapRowToDoctor, departmentID);
    }

    @Override
    public int deleteById(int doctorID) {
        if (doctorID <= 0) {
            throw new IllegalArgumentException("Doctor ID must be positive");
        }

        String sql = "DELETE FROM Doctors WHERE doctorID = ?";
        return jdbcTemplate.update(sql, doctorID);
    }

    private Doctor mapRowToDoctor(ResultSet rs, int rowNum) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setDoctorID(rs.getInt("doctorID"));
        doctor.setUserID(rs.getInt("userID"));
        doctor.setSpecialization(rs.getString("specialization"));
        doctor.setDepartmentID(rs.getInt("departmentID"));
        doctor.setFees(rs.getDouble("fees"));
        doctor.setRating(rs.getBigDecimal("rating"));
        return doctor;
    }
}
