package com.example.demo1.service;

import com.example.demo1.model.Bill;
import java.util.List;

public interface BillService {
    void save(Bill bill);
    Bill findById(int billID);
    List<Bill> findAll();
    void update(Bill bill);
    void delete(int billID);
    List<Bill> findByPatientId(int patientID);
}
