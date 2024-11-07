package com.example.demo1.service;

import com.example.demo1.model.Notification;
import com.example.demo1.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void save(Notification notification) {
        if (notification != null) {
            notificationRepository.save(notification);
        }
    }

    @Override
    public Notification findById(int notificationID) {
        return notificationRepository.findById(notificationID);
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public int update(Notification notification) {
        if (notification != null && notification.getNotificationID() > 0) {
            notificationRepository.update(notification);
            return 1; // Return success indicator
        }
        return 0; // Return failure indicator
    }

    @Override
    public int delete(int notificationID) {
        if (notificationID > 0) {
            notificationRepository.delete(notificationID);
            return notificationID; // Return the ID of the deleted notification
        }
        return -1; // Indicate failure (invalid ID)
    }

    @Override
    public List<Notification> findByUserId(int userID) {
        return notificationRepository.findByUserId(userID); // New method implementation
    }

    @Override
    public List<Notification> findByAppointmentId(int appointmentID) {
        return notificationRepository.findByAppointmentId(appointmentID); // New method implementation
    }
}