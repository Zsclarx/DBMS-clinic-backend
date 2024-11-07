package com.example.demo1.service;

import com.example.demo1.model.Doctor;

import java.util.List;

public interface DoctorService {
    int saveDoctor(Doctor doctor);
    int updateDoctor(Doctor doctor);
    Doctor findDoctorById(int doctorID);
    List<Doctor> findAllDoctors();
    int deleteDoctorById(int doctorID);

    // New method to find doctors by department ID
    List<Doctor> findDoctorsByDepartmentId(int departmentID);
}
