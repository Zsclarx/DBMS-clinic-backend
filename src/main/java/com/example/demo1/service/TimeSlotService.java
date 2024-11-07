package com.example.demo1.service;

import com.example.demo1.model.TimeSlot;

import java.sql.Time;
import java.util.List;

public interface TimeSlotService {
    void save(TimeSlot timeSlot);
    TimeSlot findById(int doctorID, Time timeslot);
    List<TimeSlot> findAll();
    void update(TimeSlot timeSlot);
    void delete(int doctorID, Time timeslot);
    List<TimeSlot> findAllByDoctorId(int doctorID); // Add this line
}