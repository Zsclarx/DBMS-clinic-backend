package com.example.demo1.repository;

import com.example.demo1.model.TimeSlot;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TimeSlotRepositoryImpl implements TimeSlotRepository {
    private final DataSource dataSource;

    public TimeSlotRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(TimeSlot timeSlot) {
        String query = "INSERT INTO TimeSlots (doctorID, timeslot, workday) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, timeSlot.getDoctorID());
            stmt.setTime(2, timeSlot.getTimeslot());
            stmt.setString(3, timeSlot.getWorkday());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TimeSlot findById(int doctorID, java.sql.Time timeSlot) {
        TimeSlot result = null;
        String query = "SELECT * FROM TimeSlots WHERE doctorID = ? AND timeslot = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorID);
            stmt.setTime(2, timeSlot);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = new TimeSlot();
                result.setDoctorID(rs.getInt("doctorID"));
                result.setTimeslot(rs.getTime("timeslot"));
                result.setWorkday(rs.getString("workday"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<TimeSlot> findAll() {
        List<TimeSlot> timeSlots = new ArrayList<>();
        String query = "SELECT * FROM TimeSlots";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TimeSlot timeSlot = new TimeSlot();
                timeSlot.setDoctorID(rs.getInt("doctorID"));
                timeSlot.setTimeslot(rs.getTime("timeslot"));
                timeSlot.setWorkday(rs.getString("workday"));
                timeSlots.add(timeSlot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timeSlots;
    }

    @Override
    public void update(TimeSlot timeSlot) {
        String query = "UPDATE TimeSlots SET workday = ? WHERE doctorID = ? AND timeslot = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, timeSlot.getWorkday());
            stmt.setInt(2, timeSlot.getDoctorID());
            stmt.setTime(3, timeSlot.getTimeslot());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int doctorID, java.sql.Time timeSlot) {
        String query = "DELETE FROM TimeSlots WHERE doctorID = ? AND timeslot = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorID);
            stmt.setTime(2, timeSlot);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TimeSlot> findAllByDoctorId(int doctorID) {
        List<TimeSlot> timeSlots = new ArrayList<>();
        String query = "SELECT * FROM TimeSlots WHERE doctorID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TimeSlot timeSlot = new TimeSlot();
                timeSlot.setDoctorID(rs.getInt("doctorID"));
                timeSlot.setTimeslot(rs.getTime("timeslot"));
                timeSlot.setWorkday(rs.getString("workday"));
                timeSlots.add(timeSlot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timeSlots;
    }

    @Override
    public List<TimeSlot> getTimeslotsByWorkday(String workday) {
        List<TimeSlot> timeSlots = new ArrayList<>();
        String query = "SELECT * FROM TimeSlots WHERE workday = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, workday);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TimeSlot timeSlot = new TimeSlot();
                timeSlot.setDoctorID(rs.getInt("doctorID"));
                timeSlot.setTimeslot(rs.getTime("timeslot"));
                timeSlot.setWorkday(rs.getString("workday"));
                timeSlots.add(timeSlot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timeSlots;
    }
}