package com.example.demo1.repository;

import com.example.demo1.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Notification> rowMapper = new RowMapper<Notification>() {
        @Override
        public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
            Notification notification = new Notification();
            notification.setNotificationID(rs.getInt("notificationID"));
            notification.setUserID(rs.getInt("userID"));
            notification.setAppointmentID(rs.getInt("appointmentID"));
            notification.setMessage(rs.getString("message"));
            notification.setDateSent(rs.getDate("dateSent"));
            return notification;
        }
    };

    @Override
    public void save(Notification notification) {
        String sql = "INSERT INTO notifications (userID, appointmentID, message, dateSent) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, notification.getUserID(), notification.getAppointmentID(),
                notification.getMessage(), notification.getDateSent());
    }

    @Override
    public Notification findById(int notificationID) {
        String sql = "SELECT * FROM notifications WHERE notificationID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{notificationID}, rowMapper);
    }

    @Override
    public List<Notification> findAll() {
        String sql = "SELECT * FROM notifications";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void update(Notification notification) {
        String sql = "UPDATE notifications SET userID = ?, appointmentID = ?, message = ?, dateSent = ? WHERE notificationID = ?";
        jdbcTemplate.update(sql, notification.getUserID(), notification.getAppointmentID(),
                notification.getMessage(), notification.getDateSent(),
                notification.getNotificationID());
    }

    @Override
    public void delete(int notificationID) {
        String sql = "DELETE FROM notifications WHERE notificationID = ?";
        jdbcTemplate.update(sql, notificationID);
    }

    @Override
    public List<Notification> findByUserId(int userID) {
        String sql = "SELECT * FROM notifications WHERE userID = ?";
        return jdbcTemplate.query(sql, new Object[]{userID}, rowMapper);
    }

    @Override
    public List<Notification> findByAppointmentId(int appointmentID) {
        String sql = "SELECT * FROM notifications WHERE appointmentID = ?";
        return jdbcTemplate.query(sql, new Object[]{appointmentID}, rowMapper);
    }
}