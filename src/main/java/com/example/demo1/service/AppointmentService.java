package com.example.demo1.service;

import com.example.demo1.model.Appointment;
import java.util.List;

public interface AppointmentService {
    Appointment save(Appointment appointment);
    Appointment findById(int id);
    List<Appointment> findAll();
    Appointment update(Appointment appointment);
    void delete(int id);
    public Appointment updatefeedback(Appointment appointment);
    List<Appointment> findByPatientId(int patientId);
}