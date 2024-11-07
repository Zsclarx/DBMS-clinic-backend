package com.example.demo1.service;

import com.example.demo1.model.Staff;

import java.util.List;

public interface StaffService {
    void save(Staff staff);
    Staff findById(int staffID);
    List<Staff> findAll();
    void update(Staff staff);
    void delete(int staffID);
}
