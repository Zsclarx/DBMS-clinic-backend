package com.example.demo1.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.example.demo1.model.DoctorAvailability;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

@Repository
public class DoctorAvailabilityRepository {

    private final JdbcTemplate jdbcTemplate;

    public DoctorAvailabilityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper implementation
    private static class DoctorAvailabilityRowMapper implements RowMapper<DoctorAvailability> {
        @Override
        public DoctorAvailability mapRow(ResultSet rs, int rowNum) throws SQLException {
            DoctorAvailability availability = new DoctorAvailability();
            availability.setAvailabilityId(rs.getInt("AvailabilityID"));
            availability.setDoctorId(rs.getInt("DoctorID"));
            availability.setTimeOfService(rs.getTime("TimeOfService"));
            availability.setDate(rs.getDate("Date"));
            availability.setStatus(rs.getString("Status"));
            // Removed appointmentId mapping since it no longer exists
            return availability;
        }
    }

    public List<DoctorAvailability> getDoctorAvailabilityByDate(int doctorId, Date date) {
        String sql = "SELECT * FROM DoctorAvailability WHERE DoctorID = ? AND Date = ?";
        return jdbcTemplate.query(sql, new Object[]{doctorId, date}, new DoctorAvailabilityRowMapper());
    }

    public List<DoctorAvailability> getDoctorAvailabilityForNextWeek(int doctorId) {
        String sql = "SELECT * FROM DoctorAvailability WHERE DoctorID = ? AND Date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 6 DAY)";
        return jdbcTemplate.query(sql, new Object[]{doctorId}, new DoctorAvailabilityRowMapper());
    }

    public void deleteOldAvailability() {
        String sql = "DELETE FROM DoctorAvailability WHERE Date < CURDATE()";
        jdbcTemplate.update(sql);
    }

    public void insertAvailability(int doctorId, Time time, Date date, String status) {
        String sql = "INSERT INTO DoctorAvailability (DoctorID, TimeOfService, Date, Status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, doctorId, time, date, status);
    }

    public void updateAvailabilityStatus(int availabilityId, String status) {
        String sql = "UPDATE DoctorAvailability SET Status = ? WHERE AvailabilityID = ?";
        jdbcTemplate.update(sql, status, availabilityId);
    }

    public Date getMaxDate() {
        String sql = "SELECT MAX(Date) FROM DoctorAvailability";
        return jdbcTemplate.queryForObject(sql, Date.class);
    }
}
