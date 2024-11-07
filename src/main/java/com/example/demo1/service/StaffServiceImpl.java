package com.example.demo1.service;

import com.example.demo1.model.Staff;
import com.example.demo1.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    @Autowired
    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public void save(Staff staff) {
        staffRepository.save(staff);
    }

    @Override
    public Staff findById(int staffID) {
        return staffRepository.findById(staffID);
    }

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public void update(Staff staff) {
        staffRepository.update(staff);
    }

    @Override
    public void delete(int staffID) {
        staffRepository.delete(staffID);
    }
}
