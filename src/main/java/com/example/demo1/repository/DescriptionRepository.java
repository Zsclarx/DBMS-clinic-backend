package com.example.demo1.repository;

import com.example.demo1.model.Description;

import java.util.List;

public interface DescriptionRepository {
    int save(Description description);
    int update(Description description);
    Description findById(int appointmentID);
    List<Description> findAll();
    int deleteById(int appointmentID);
}
