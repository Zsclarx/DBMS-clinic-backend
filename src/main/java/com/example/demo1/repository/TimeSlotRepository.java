package com.example.demo1.repository;

import com.example.demo1.model.TimeSlot;

import java.util.List;

public interface TimeSlotRepository {
    void save(TimeSlot timeSlot);
    TimeSlot findById(int doctorID, java.sql.Time timeSlot);
    List<TimeSlot> findAll();
    void update(TimeSlot timeSlot);
    void delete(int doctorID, java.sql.Time timeSlot);
    List<TimeSlot> findAllByDoctorId(int doctorID);
    List<TimeSlot> getTimeslotsByWorkday(String workday);


}