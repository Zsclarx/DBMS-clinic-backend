package com.example.demo1.controller;

import com.example.demo1.model.MedicationDetail;
import com.example.demo1.service.MedicationDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/medication-details")
public class MedicationDetailController {
    private final MedicationDetailService medicationDetailService;

    public MedicationDetailController(MedicationDetailService medicationDetailService) {
        this.medicationDetailService = medicationDetailService;
    }

    @PostMapping // Save MedicationDetail
    public ResponseEntity<String> save(@RequestBody MedicationDetail medicationDetail) {
        try {
            medicationDetailService.save(medicationDetail);
            return ResponseEntity.ok("Medication detail saved successfully");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Error saving medication detail: " + e.getMessage());
        }
    }

    @GetMapping("/{prescriptionID}") // Get MedicationDetails by PrescriptionID
    public ResponseEntity<List<MedicationDetail>> getByPrescriptionId(@PathVariable int prescriptionID) {
        try {
            List<MedicationDetail> details = medicationDetailService.findByPrescriptionId(prescriptionID);
            if (details.isEmpty()) {
                return ResponseEntity.status(404).body(null); // Return 404 if not found
            }
            return ResponseEntity.ok(details);
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping // Get all MedicationDetails
    public ResponseEntity<List<MedicationDetail>> getAll() {
        try {
            List<MedicationDetail> details = medicationDetailService.findAll();
            return ResponseEntity.ok(details);
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{prescriptionID}") // Update MedicationDetail by PrescriptionID
    public ResponseEntity<String> update(@PathVariable int prescriptionID, @RequestBody MedicationDetail medicationDetail) {
        try {
            // Check if the medication detail exists
            List<MedicationDetail> details = medicationDetailService.findByPrescriptionId(prescriptionID);
            if (details.isEmpty()) {
                return ResponseEntity.status(404).body("No medication details found for the provided prescription ID");
            }

            // Set the new advice and update the medication detail
            MedicationDetail existingDetail = details.get(0); // Get the first existing detail
            existingDetail.setAdvice(medicationDetail.getAdvice());
            medicationDetailService.update(existingDetail); // Call the update service method

            return ResponseEntity.ok("Medication detail updated successfully");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Error updating medication detail: " + e.getMessage());
        }
    }

    @DeleteMapping("/{prescriptionID}") // Delete MedicationDetail by PrescriptionID
    public ResponseEntity<String> delete(@PathVariable int prescriptionID) {
        medicationDetailService.delete(prescriptionID);
        return ResponseEntity.ok("Medication detail deleted successfully");
    }
}
