package com.example.demo1.repository;

import com.example.demo1.model.Doctor;

import java.util.List;

public interface DoctorRepository {
    int save(Doctor doctor);
    int update(Doctor doctor);
    Doctor findById(int doctorID);
    List<Doctor> findAll();
    int deleteById(int doctorID);

    // New method to find doctors by department ID
    List<Doctor> findByDepartmentId(int departmentID);
}
