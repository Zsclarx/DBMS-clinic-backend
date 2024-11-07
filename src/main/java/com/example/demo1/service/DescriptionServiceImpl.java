package com.example.demo1.service;

import com.example.demo1.model.Description;
import com.example.demo1.repository.DescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescriptionServiceImpl implements DescriptionService {

    private final DescriptionRepository descriptionRepository;

    public DescriptionServiceImpl(DescriptionRepository descriptionRepository) {
        this.descriptionRepository = descriptionRepository;
    }

    @Override
    public int saveDescription(Description description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        return descriptionRepository.save(description);
    }

    @Override
    public int updateDescription(Description description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        return descriptionRepository.update(description);
    }

    @Override
    public Description findDescriptionById(int appointmentID) {
        if (appointmentID <= 0) {
            throw new IllegalArgumentException("Appointment ID must be positive");
        }
        return descriptionRepository.findById(appointmentID);
    }

    @Override
    public List<Description> findAllDescriptions() {
        return descriptionRepository.findAll();
    }

    @Override
    public int deleteDescriptionById(int appointmentID) {
        if (appointmentID <= 0) {
            throw new IllegalArgumentException("Appointment ID must be positive");
        }
        return descriptionRepository.deleteById(appointmentID);
    }
}
