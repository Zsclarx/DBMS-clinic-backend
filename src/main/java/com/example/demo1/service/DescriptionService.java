package com.example.demo1.service;

import com.example.demo1.model.Description;

import java.util.List;

public interface DescriptionService {
    int saveDescription(Description description);
    int updateDescription(Description description);
    Description findDescriptionById(int appointmentID);
    List<Description> findAllDescriptions();
    int deleteDescriptionById(int appointmentID);
}
