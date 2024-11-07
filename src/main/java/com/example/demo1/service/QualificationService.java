package com.example.demo1.service;

import com.example.demo1.model.Qualification;

import java.util.List;

public interface QualificationService {
    void save(Qualification qualification);
    List<Qualification> findByDoctorId(int doctorID);
    List<Qualification> findAll();
    void delete(int doctorID, String degree);
}
