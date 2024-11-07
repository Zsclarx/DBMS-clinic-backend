package com.example.demo1.controller;

import com.example.demo1.model.Patient;
import com.example.demo1.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // GET: Retrieve a patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable int id) {
        Patient patient = patientService.findById(id);
        if (patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    // GET: Retrieve all patients
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.findAll();
    }

    // POST: Save a new patient
    @PostMapping("/save")
    public ResponseEntity<String> savePatient(@RequestBody Patient patient) {
        if (patient == null || patient.getUserID() == 0) {
            return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);
        }

        patientService.save(patient);
        return new ResponseEntity<>("Patient saved successfully", HttpStatus.CREATED);
    }

    // PUT: Update an existing patient
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable int id, @RequestBody Patient patient) {
        if (patientService.findById(id) == null) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }

        patient.setPatientID(id);
        patientService.update(patient);
        return new ResponseEntity<>("Patient updated successfully", HttpStatus.OK);
    }

    // DELETE: Delete a patient by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable int id) {
        if (patientService.findById(id) == null) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }

        patientService.delete(id);
        return new ResponseEntity<>("Patient deleted successfully", HttpStatus.OK);
    }
}
