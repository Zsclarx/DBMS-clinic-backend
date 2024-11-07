package com.example.demo1.repository;

import com.example.demo1.model.Department;
import java.util.List;

public interface DepartmentRepository {
    void save(Department department);
    Department findById(int departmentID);
    List<Department> findAll();
    void update(Department department);
    void delete(int departmentID);

    boolean existsByNameOrDescription(String departmentName, String description);
}
