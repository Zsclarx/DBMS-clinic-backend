package com.example.demo1.repository;

import com.example.demo1.model.Appointment;
import java.sql.Date;
import java.util.List;

public interface AppointmentRepository {
    Appointment save(Appointment appointment);

    // Find appointments by patientID
    List<Appointment> findByPatientId(int patientID);
    public Appointment updatefeedback(Appointment appointment);
    Appointment findById(int appointmentID);
    List<Appointment> findAll();
    Appointment update(Appointment appointment);
    void delete(int appointmentID);
}
