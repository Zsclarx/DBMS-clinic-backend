package com.example.demo1.controller;

import com.example.demo1.model.Doctor;
import com.example.demo1.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<String> createDoctor(@RequestBody Doctor doctor) {
        if (doctor == null) {
            return ResponseEntity.badRequest().body("Doctor cannot be null");
        }
        int result = doctorService.saveDoctor(doctor);
        return ResponseEntity.ok("Doctor saved with ID: " + result);
    }
    @PostMapping("/feedback/{doctorID}")
    public ResponseEntity<String> submitFeedback(@PathVariable int doctorID, @RequestBody Integer rating) {
        if (rating == null || rating < 0) {
            return ResponseEntity.badRequest().body("Invalid feedback data");
        }

        // Fetch current doctor's rating
        Doctor currentDoctor = doctorService.findDoctorById(doctorID);
        if (currentDoctor == null) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found
        }

        // Assuming doctor.getRating() gives the string "totalRating.userCount"
        String ratingString = currentDoctor.getRating().toString(); // Get the rating as a String
        String[] ratingParts = ratingString.split("\\."); // Split the string by "."

        // Extract current total rating and user count
        BigDecimal currentTotalRating = new BigDecimal(ratingParts[0]); // Total rating points
        int currentUserCount = Integer.parseInt(ratingParts[1]); // Total number of users

        // Update total ratings and user count
        BigDecimal newRating = BigDecimal.valueOf(rating); // New rating coming from feedback
        BigDecimal updatedTotalRating = currentTotalRating.add(newRating); // Update total rating
        int updatedUserCount = currentUserCount + 1; // Increment user count

        // Calculate new average rating
        BigDecimal newAverageRating = updatedTotalRating.divide(BigDecimal.valueOf(updatedUserCount), 2, RoundingMode.HALF_UP);

        // Update the doctor's rating directly as BigDecimal
        currentDoctor.setRating(newAverageRating);

        // Save updated doctor information
        doctorService.updateDoctor(currentDoctor);

        return ResponseEntity.ok("Feedback submitted and doctor rating updated successfully.");
    }

    @PutMapping("/{doctorID}")
    public ResponseEntity<String> updateDoctor(@RequestBody Doctor doctor) {
        if (doctor == null) {
            return ResponseEntity.badRequest().body("Doctor cannot be null");
        }
        int result = doctorService.updateDoctor(doctor);
        return ResponseEntity.ok("Doctor updated with ID: " + result);
    }

    @GetMapping("/{doctorID}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable int doctorID) {
        if (doctorID <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Doctor doctor = doctorService.findDoctorById(doctorID);
        return ResponseEntity.ok(doctor);
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.findAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/department/{departmentID}")
    public ResponseEntity<List<Doctor>> getDoctorsByDepartmentId(@PathVariable int departmentID) {
        if (departmentID <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Doctor> doctors = doctorService.findDoctorsByDepartmentId(departmentID);
        return ResponseEntity.ok(doctors);
    }

    @DeleteMapping("/{doctorID}")
    public ResponseEntity<String> deleteDoctor(@PathVariable int doctorID) {
        if (doctorID <= 0) {
            return ResponseEntity.badRequest().body("Doctor ID must be positive");
        }
        doctorService.deleteDoctorById(doctorID);
        return ResponseEntity.ok("Doctor deleted with ID: " + doctorID);
    }
}
