package com.example.demo1.repository;

import com.example.demo1.model.Feedback;

import java.util.List;

public interface FeedbackRepository {
    int save(Feedback feedback);
    int update(Feedback feedback);
    Feedback findById(int feedbackID);
    List<Feedback> findAll();
    List<Feedback> findByAppointmentId(int appointmentID); // New method
    int deleteById(int feedbackID);
}
