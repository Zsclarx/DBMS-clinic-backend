package com.example.demo1.repository;

import com.example.demo1.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper to map ResultSet to Appointment object
    private final RowMapper<Appointment> rowMapper = new RowMapper<Appointment>() {
        @Override
        public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(rs.getInt("appointmentID"));
            appointment.setPatientID(rs.getInt("patientID"));
            appointment.setDoctorID(rs.getInt("doctorID"));
            appointment.setAppointmentDate(rs.getDate("appointmentDate"));
            appointment.setTime(rs.getTime("time"));
            appointment.setStatus(rs.getString("status"));
            appointment.setAmount(rs.getDouble("amount"));
            appointment.setDateIssued(rs.getDate("dateIssued"));
            appointment.setRating(rs.getInt("rating"));
            appointment.setComments(rs.getString("comments"));
            return appointment;
        }
    };

    // Save new appointment
    @Override
    public Appointment save(Appointment appointment) {
        String insertSql = "INSERT INTO appointments (patientID, doctorID, appointmentDate, time, status, amount, dateIssued, rating, comments) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertSql, appointment.getPatientID(), appointment.getDoctorID(),
                appointment.getAppointmentDate(), appointment.getTime(), appointment.getStatus(),
                appointment.getAmount(), appointment.getDateIssued(), appointment.getRating(), appointment.getComments());

        String selectSql = "SELECT * FROM appointments WHERE patientID = ? AND doctorID = ? ORDER BY appointmentID DESC LIMIT 1";

        return jdbcTemplate.queryForObject(selectSql, new Object[]{appointment.getPatientID(), appointment.getDoctorID()},
                rowMapper);
    }

    // Find appointments by patientID
    @Override
    public List<Appointment> findByPatientId(int patientID) {
        String sql = "SELECT * FROM appointments WHERE patientID = ?";
        return jdbcTemplate.query(sql, new Object[]{patientID}, rowMapper);
    }



    // Find appointment by ID, returns null if not found
    @Override
    public Appointment findById(int appointmentID) {
        String sql = "SELECT * FROM appointments WHERE appointmentID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{appointmentID}, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;  // Handle case when no record is found
        }
    }

    // Find all appointments, returns an empty list if no records are found
    @Override
    public List<Appointment> findAll() {
        String sql = "SELECT * FROM appointments";
        return jdbcTemplate.query(sql, rowMapper);  // Returns an empty list if no results
    }

    // Update appointment

    @Override
    public Appointment updatefeedback(Appointment appointment) {
        String sql = "UPDATE appointments SET patientID = ?, doctorID = ?, appointmentDate = ?, time = ?, status = ?, amount = ?, dateIssued = ?, rating = ?, comments = ? WHERE appointmentID = ?";
        jdbcTemplate.update(sql, appointment.getPatientID(), appointment.getDoctorID(),
                appointment.getAppointmentDate(), appointment.getTime(), appointment.getStatus(),
                appointment.getAmount(), appointment.getDateIssued(), appointment.getRating(),
                appointment.getComments(), appointment.getAppointmentID());
        return appointment;
    }
    @Override
    public Appointment update(Appointment appointment) {
        String sql = "UPDATE appointments SET status = ? WHERE appointmentID = ?";
        jdbcTemplate.update(sql,appointment.getStatus(),
                appointment.getAppointmentID());
        String selectSql = "SELECT * FROM appointments WHERE appointmentID = ?";

        return jdbcTemplate.queryForObject(selectSql, new Object[]{appointment.getAppointmentID()},
                rowMapper);

    }

    // Delete appointment by ID
    @Override
    public void delete(int appointmentID) {
        String sql = "DELETE FROM appointments WHERE appointmentID = ?";
        jdbcTemplate.update(sql, appointmentID);
    }
}