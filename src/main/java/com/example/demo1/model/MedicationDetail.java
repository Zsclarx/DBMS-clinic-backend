package com.example.demo1.model;

public class MedicationDetail {
    private int prescriptionID;
    private String advice;

    public MedicationDetail() {
    }

    // Getters and Setters
    // ...

    public int getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
