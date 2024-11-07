package com.example.demo1.controller;

import com.example.demo1.model.TimeSlot;
import com.example.demo1.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/api/timeslots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @Autowired
    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @PostMapping
    public ResponseEntity<Void> createTimeSlot(@RequestBody TimeSlot timeSlot) {
        try {
            timeSlotService.save(timeSlot);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{doctorID}/{timeslot}")
    public ResponseEntity<TimeSlot> getTimeSlot(@PathVariable int doctorID, @PathVariable Time timeslot) {
        try {
            TimeSlot timeSlot = timeSlotService.findById(doctorID, timeslot);
            return timeSlot != null
                    ? ResponseEntity.ok(timeSlot)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TimeSlot>> getAllTimeSlots() {
        List<TimeSlot> timeSlots = timeSlotService.findAll();
        return ResponseEntity.ok(timeSlots);
    }

    @GetMapping("/doctor/{doctorID}")
    public ResponseEntity<List<TimeSlot>> findByDoctorId(@PathVariable int doctorID) {
        try {
            List<TimeSlot> timeSlots = timeSlotService.findAllByDoctorId(doctorID);
            return ResponseEntity.ok(timeSlots);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Void> updateTimeSlot(@RequestBody TimeSlot timeSlot) {
        try {
            timeSlotService.update(timeSlot);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{doctorID}/{timeslot}")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable int doctorID, @PathVariable Time timeslot) {
        try {
            timeSlotService.delete(doctorID, timeslot);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}