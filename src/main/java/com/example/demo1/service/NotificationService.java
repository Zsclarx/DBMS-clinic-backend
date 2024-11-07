package com.example.demo1.service;

import com.example.demo1.model.Notification;
import java.util.List;

public interface NotificationService {
    void save(Notification notification);
    Notification findById(int notificationID);
    List<Notification> findAll();
    int update(Notification notification);
    int delete(int notificationID);
    List<Notification> findByUserId(int userID); // New method
    List<Notification> findByAppointmentId(int appointmentID); // New method
}