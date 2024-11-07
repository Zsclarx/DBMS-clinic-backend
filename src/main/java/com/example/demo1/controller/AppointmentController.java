package com.example.demo1.controller;

import com.example.demo1.model.Appointment;
import com.example.demo1.model.Notification;
import com.example.demo1.service.AppointmentService;
import com.example.demo1.service.NotificationService; // Use the interface instead of implementation
import com.example.demo1.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired // Autowire the NotificationService
    private NotificationService notificationService;

    @Autowired
    private PatientService patientService; // Inject PatientService to access patient details


    @PostMapping("/save")
    public ResponseEntity<Void> saveAppointment(@RequestBody Appointment appointment) {
        Appointment newAppointment = appointmentService.save(appointment);
        Notification notification = new Notification();

        // Retrieve userID using patientID
        Integer userID = patientService.findById(newAppointment.getPatientID()).getUserID();
        if (userID != null) {
            notification.setUserID(userID);
        } else {
            return ResponseEntity.badRequest().build(); // Return 400 if userID not found
        }

        notification.setAppointmentID(newAppointment.getAppointmentID());
        notification.setMessage("Your appointment on " + newAppointment.getAppointmentDate() + " is " + newAppointment.getStatus());
        notificationService.save(notification);
        return ResponseEntity.status(201).build(); // Return 201 Created
    }


    // Get an appointment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable int id) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment == null) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found
        }
        return ResponseEntity.ok(appointment); // Return 200 OK and the appointment
    }

    // Get all appointments
    @GetMapping("/all")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.findAll();
        return ResponseEntity.ok(appointments); // Return 200 OK and the list of appointments
    }

    // Update an existing appointment
    @PutMapping("/update")
    public ResponseEntity<Void> updateAppointment(@RequestBody Appointment appointment) {
        // You might want to check if the appointment exists before updating
        Appointment newAppointment = appointmentService.update(appointment);
//        Notification notification = new Notification();
//        notification.setUserID(newAppointment.getPatientID());
//        notification.setAppointmentID(newAppointment.getAppointmentID());
//        notification.setMessage("Your appointment on " + newAppointment.getAppointmentDate() + " is " + newAppointment.getStatus());
//        notificationService.save(notification);
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }
    @PutMapping("/feedback/{id}")
    public ResponseEntity<Void> updateFeedback(@PathVariable int id, @RequestBody Appointment appointmentFeedback) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment == null) {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
        appointment.setRating(appointmentFeedback.getRating());
        appointment.setComments(appointmentFeedback.getComments());
        appointmentService.updatefeedback(appointment);
        return ResponseEntity.noContent().build(); // Return 204 No Content on success
    }

    // Delete an appointment by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable int id) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment == null) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found
        }
        appointmentService.delete(id);
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }

    // Get all appointments by patient ID
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientId(@PathVariable int patientId) {
        List<Appointment> appointments = appointmentService.findByPatientId(patientId);
        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no appointments found
        }
        return ResponseEntity.ok(appointments); // Return 200 OK and the list of appointments
    }
}