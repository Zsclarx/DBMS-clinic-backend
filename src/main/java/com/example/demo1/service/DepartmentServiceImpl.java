package com.example.demo1.service;

import com.example.demo1.model.Department;
import com.example.demo1.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void save(Department department) {
        if (department == null || department.getDepartmentName() == null) {
            throw new IllegalArgumentException("Department or Department Name cannot be null");
        }

        // Check for existing department with the same name or description
        if (departmentRepository.existsByNameOrDescription(department.getDepartmentName(), department.getDescription())) {
            throw new IllegalArgumentException("Department with this name or description already exists.");
        }

        departmentRepository.save(department);
    }

    @Override
    public Department findById(int departmentID) {
        if (departmentID <= 0) {
            throw new IllegalArgumentException("Invalid Department ID");
        }
        return departmentRepository.findById(departmentID);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public void update(Department department) {
        if (department == null || department.getDepartmentID() <= 0) {
            throw new IllegalArgumentException("Department or Department ID cannot be null or invalid");
        }
        departmentRepository.update(department);
    }

    @Override
    public void delete(int departmentID) {
        if (departmentID <= 0) {
            throw new IllegalArgumentException("Department ID cannot be null or invalid");
        }
        departmentRepository.delete(departmentID);
    }
}