package com.example.demo1.service;

import com.example.demo1.model.Feedback;
import com.example.demo1.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public int saveFeedback(Feedback feedback) {
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback cannot be null");
        }
        return feedbackRepository.save(feedback);
    }

    @Override
    public int updateFeedback(Feedback feedback) {
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback cannot be null");
        }
        if (feedback.getFeedbackID() <= 0) {
            throw new IllegalArgumentException("Feedback ID must be positive");
        }
        return feedbackRepository.update(feedback);
    }

    @Override
    public Feedback findFeedbackById(int feedbackID) {
        if (feedbackID <= 0) {
            throw new IllegalArgumentException("Feedback ID must be positive");
        }
        return feedbackRepository.findById(feedbackID);
    }

    @Override
    public List<Feedback> findAllFeedback() {
        return feedbackRepository.findAll();
    }

    @Override
    public List<Feedback> findFeedbackByAppointmentId(int appointmentID) {
        if (appointmentID <= 0) {
            throw new IllegalArgumentException("Appointment ID must be positive");
        }
        return feedbackRepository.findByAppointmentId(appointmentID);
    }

    @Override
    public int deleteFeedbackById(int feedbackID) {
        if (feedbackID <= 0) {
            throw new IllegalArgumentException("Feedback ID must be positive");
        }
        return feedbackRepository.deleteById(feedbackID);
    }
}
