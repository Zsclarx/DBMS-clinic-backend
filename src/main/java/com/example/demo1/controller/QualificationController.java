package com.example.demo1.controller;

import com.example.demo1.model.Qualification;
import com.example.demo1.service.QualificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qualifications")
public class QualificationController {
    private final QualificationService qualificationService;

    public QualificationController(QualificationService qualificationService) {
        this.qualificationService = qualificationService;
    }

    // Save a new qualification
    @PostMapping("/save") // Unique mapping for saving
    public ResponseEntity<String> saveQualification(@RequestBody Qualification qualification) {
        try {
            qualificationService.save(qualification);
            return ResponseEntity.status(HttpStatus.CREATED).body("Qualification added successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Get qualifications by doctor ID
    @GetMapping("/get/{doctorID}") // Unique mapping for getting qualifications by doctor ID
    public ResponseEntity<List<Qualification>> getQualificationsByDoctorId(@PathVariable int doctorID) {
        try {
            List<Qualification> qualifications = qualificationService.findByDoctorId(doctorID);

            // Return empty list with 200 OK when no qualifications found
            return ResponseEntity.ok(qualifications);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    // Get all qualifications
    @GetMapping("/getAll") // Unique mapping for getting all qualifications
    public ResponseEntity<List<Qualification>> getAllQualifications() {
        List<Qualification> qualifications = qualificationService.findAll();
        return ResponseEntity.ok(qualifications);
    }

    // Delete a qualification
    @DeleteMapping("/delete") // Unique mapping for deleting a qualification
    public ResponseEntity<String> deleteQualification(@RequestParam int doctorID, @RequestParam String degree) {
        try {
            qualificationService.delete(doctorID, degree);
            return ResponseEntity.ok("Qualification deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
