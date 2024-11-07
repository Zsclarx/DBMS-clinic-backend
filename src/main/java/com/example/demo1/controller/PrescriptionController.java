package com.example.demo1.controller;

import com.example.demo1.model.Prescription;
import com.example.demo1.service.PrescriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable int id) {
        try {
            Prescription prescription = prescriptionService.findById(id);
            return new ResponseEntity<>(prescription, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionService.findAll();
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }
    @GetMapping("/appointment/{appointmentID}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatientId(@PathVariable int appointmentID) {
        List<Prescription> prescriptions = prescriptionService.findByappointmentId(appointmentID);
        if (prescriptions.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content
        }
        return ResponseEntity.ok(prescriptions); // Return 200 OK and the list of prescriptions
    }


    @PostMapping
    public ResponseEntity<Void> createPrescription(@RequestBody Prescription prescription) {
        try {
            prescriptionService.save(prescription);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<Void> updatePrescription(@RequestBody Prescription prescription) {
        try {
            prescriptionService.update(prescription);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable int id) {
        try {
            prescriptionService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}