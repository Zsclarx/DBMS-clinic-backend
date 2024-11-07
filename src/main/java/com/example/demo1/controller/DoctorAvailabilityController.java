package com.example.demo1.controller;

import com.example.demo1.model.DoctorAvailability;
import com.example.demo1.service.DoctorAvailabilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/doctor-availability")
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService availabilityService;

    public DoctorAvailabilityController(DoctorAvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping("/{doctorId}/date")
    public ResponseEntity<List<DoctorAvailability>> getAvailabilityByDate(
            @PathVariable int doctorId, @RequestParam Date date) {
        if (doctorId <= 0) {
            return ResponseEntity.badRequest().body(null); // Bad request for invalid doctorId
        }

        List<DoctorAvailability> availability = availabilityService.getAvailabilityByDate(doctorId, date);
        if (availability.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no availability found
        }

        return ResponseEntity.ok(availability); // Return 200 OK with the availability list
    }

    @GetMapping("/{doctorId}/week")
    public ResponseEntity<List<DoctorAvailability>> getAvailabilityForNextWeek(
            @PathVariable int doctorId) {
        if (doctorId <= 0) {
            return ResponseEntity.badRequest().body(null); // Bad request for invalid doctorId
        }

        List<DoctorAvailability> availability = availabilityService.getAvailabilityForNextWeek(doctorId);
        if (availability.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no availability found
        }

        return ResponseEntity.ok(availability); // Return 200 OK with the availability list
    }
}
