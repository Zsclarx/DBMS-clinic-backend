package com.example.demo1.controller;

import com.example.demo1.model.Description;
import com.example.demo1.service.DescriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/descriptions")
public class DescriptionController {

    private final DescriptionService descriptionService;

    public DescriptionController(DescriptionService descriptionService) {
        this.descriptionService = descriptionService;
    }

    @PostMapping
    public ResponseEntity<String> createDescription(@RequestBody Description description) {
        if (description == null) {
            return ResponseEntity.badRequest().body("Description cannot be null");
        }
        int result = descriptionService.saveDescription(description);
        return ResponseEntity.ok("Description saved with Appointment ID: " + result);
    }

    @PutMapping
    public ResponseEntity<String> updateDescription(@RequestBody Description description) {
        if (description == null) {
            return ResponseEntity.badRequest().body("Description cannot be null");
        }
        int result = descriptionService.updateDescription(description);
        return ResponseEntity.ok("Description updated for Appointment ID: " + result);
    }

    @GetMapping("/{appointmentID}")
    public ResponseEntity<Description> getDescriptionById(@PathVariable int appointmentID) {
        if (appointmentID <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Description description = descriptionService.findDescriptionById(appointmentID);
        return ResponseEntity.ok(description);
    }

    @GetMapping
    public ResponseEntity<List<Description>> getAllDescriptions() {
        List<Description> descriptions = descriptionService.findAllDescriptions();
        return ResponseEntity.ok(descriptions);
    }

    @DeleteMapping("/{appointmentID}")
    public ResponseEntity<String> deleteDescription(@PathVariable int appointmentID) {
        if (appointmentID <= 0) {
            return ResponseEntity.badRequest().body("Appointment ID must be positive");
        }
        descriptionService.deleteDescriptionById(appointmentID);
        return ResponseEntity.ok("Description deleted for Appointment ID: " + appointmentID);
    }
}
