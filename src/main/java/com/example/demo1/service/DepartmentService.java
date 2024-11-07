package com.example.demo1.service;

import com.example.demo1.model.Department;
import java.util.List;

public interface DepartmentService {
    void save(Department department);
    Department findById(int departmentID);
    List<Department> findAll();
    void update(Department department);
    void delete(int departmentID);
}
