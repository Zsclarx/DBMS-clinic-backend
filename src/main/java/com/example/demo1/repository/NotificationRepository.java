package com.example.demo1.repository;

import com.example.demo1.model.Notification;
import java.util.List;

public interface NotificationRepository {
    void save(Notification notification);
    Notification findById(int notificationID);
    List<Notification> findAll();
    void update(Notification notification);
    void delete(int notificationID);
    List<Notification> findByUserId(int userID); // New method
    List<Notification> findByAppointmentId(int appointmentID); // New method
}