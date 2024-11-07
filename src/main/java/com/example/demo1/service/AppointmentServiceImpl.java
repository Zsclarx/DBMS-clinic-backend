package com.example.demo1.service;

import com.example.demo1.model.Appointment;
import com.example.demo1.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment findById(int id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment update(Appointment appointment) {
        return appointmentRepository.update(appointment);

    }

    @Override
    public Appointment updatefeedback(Appointment appointment) {
        return appointmentRepository.updatefeedback(appointment);

    }

    @Override
    public void delete(int id) {
        appointmentRepository.delete(id);
    }

    @Override
    public List<Appointment> findByPatientId(int patientID) {
        return appointmentRepository.findByPatientId(patientID);
    }



}