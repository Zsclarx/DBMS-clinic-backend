package com.example.demo1.controller;

import com.example.demo1.model.Notification;
import com.example.demo1.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Save a new notification
    @PostMapping("/save")
    public ResponseEntity<Void> saveNotification(@RequestBody Notification notification) {
        notificationService.save(notification);
        return ResponseEntity.status(201).build(); // Return 201 Created
    }

    // Get a notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable int id) {
        Notification notification = notificationService.findById(id);
        if (notification == null) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found
        }
        return ResponseEntity.ok(notification); // Return 200 OK and the notification
    }

    // Get all notifications
    @GetMapping("/all")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.findAll();
        return ResponseEntity.ok(notifications); // Return 200 OK and the list of notifications
    }

    // Get notifications by UserID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable int userId) {
        List<Notification> notifications = notificationService.findByUserId(userId);
        return ResponseEntity.ok(notifications); // Return 200 OK and the list of notifications for the user
    }

    // Get notifications by AppointmentID
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<Notification>> getNotificationsByAppointmentId(@PathVariable int appointmentId) {
        List<Notification> notifications = notificationService.findByAppointmentId(appointmentId);
        return ResponseEntity.ok(notifications); // Return 200 OK and the list of notifications for the appointment
    }

    // Update an existing notification
    @PutMapping("/update")
    public ResponseEntity<Void> updateNotification(@RequestBody Notification notification) {
        int result = notificationService.update(notification);
        if (result == 0) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request for invalid input
        }
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }

    // Delete a notification by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable int id) {
        int result = notificationService.delete(id);
        if (result == -1) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request for invalid ID
        }
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }
}