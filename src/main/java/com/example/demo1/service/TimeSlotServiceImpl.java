package com.example.demo1.service;

import com.example.demo1.model.TimeSlot;
import com.example.demo1.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public TimeSlotServiceImpl(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    @Override
    public void save(TimeSlot timeSlot) {
        validateTimeSlot(timeSlot);
        timeSlotRepository.save(timeSlot);
    }

    @Override
    public TimeSlot findById(int doctorID, Time timeslot) {
        validateDoctorId(doctorID);
        validateTime(timeslot);
        Time time = Time.valueOf(timeslot + ":00");
        return timeSlotRepository.findById(doctorID, time);
    }

    @Override
    public List<TimeSlot> findAll() {
        return timeSlotRepository.findAll();
    }

    @Override
    public void update(TimeSlot timeSlot) {
        validateTimeSlot(timeSlot);
        timeSlotRepository.update(timeSlot);
    }

    @Override
    public void delete(int doctorID, Time timeslot) {
        validateDoctorId(doctorID);
        validateTime(timeslot);
        timeSlotRepository.delete(doctorID, timeslot);
    }

    @Override
    public List<TimeSlot> findAllByDoctorId(int doctorID) {
        validateDoctorId(doctorID); // Validate the doctor ID
        return timeSlotRepository.findAllByDoctorId(doctorID); // Call the repository method
    }

    // Validation methods
    private void validateTimeSlot(TimeSlot timeSlot) {
        if (timeSlot == null) {
            throw new IllegalArgumentException("TimeSlot cannot be null");
        }
        if (timeSlot.getTimeslot() == null) {
            throw new IllegalArgumentException("Timeslot cannot be null");
        }
        if (timeSlot.getWorkday() == null || timeSlot.getWorkday().isEmpty()) {
            throw new IllegalArgumentException("Workday cannot be null or empty");
        }
    }

    private void validateDoctorId(int doctorID) {
        if (doctorID <= 0) {
            throw new IllegalArgumentException("Invalid doctorID: must be a positive integer");
        }
    }

    private void validateTime(Time timeslot) {
        if (timeslot == null) {
            throw new IllegalArgumentException("Timeslot cannot be null");
        }
    }
}