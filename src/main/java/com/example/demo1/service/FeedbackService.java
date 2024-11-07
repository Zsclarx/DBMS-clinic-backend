package com.example.demo1.service;

import com.example.demo1.model.Feedback;

import java.util.List;

public interface FeedbackService {
    int saveFeedback(Feedback feedback);
    int updateFeedback(Feedback feedback);
    Feedback findFeedbackById(int feedbackID);
    List<Feedback> findAllFeedback();
    List<Feedback> findFeedbackByAppointmentId(int appointmentID); // New method
    int deleteFeedbackById(int feedbackID);
}
