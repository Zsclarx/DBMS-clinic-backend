package com.example.demo1.service;

import com.example.demo1.model.Doctor;
import com.example.demo1.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public int saveDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null");
        }
        return doctorRepository.save(doctor);
    }

    @Override
    public int updateDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null");
        }
        if (doctor.getDoctorID() <= 0) {
            throw new IllegalArgumentException("Doctor ID must be positive");
        }
        return doctorRepository.update(doctor);
    }

    @Override
    public Doctor findDoctorById(int doctorID) {
        if (doctorID <= 0) {
            throw new IllegalArgumentException("Doctor ID must be positive");
        }
        return doctorRepository.findById(doctorID);
    }

    @Override
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public List<Doctor> findDoctorsByDepartmentId(int departmentID) {
        if (departmentID <= 0) {
            throw new IllegalArgumentException("Department ID must be positive");
        }
        return doctorRepository.findByDepartmentId(departmentID);
    }

    @Override
    public int deleteDoctorById(int doctorID) {
        if (doctorID <= 0) {
            throw new IllegalArgumentException("Doctor ID must be positive");
        }
        return doctorRepository.deleteById(doctorID);
    }
}