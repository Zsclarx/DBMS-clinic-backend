package com.example.demo1.repository;

import com.example.demo1.model.Bill;
import java.sql.Date;
import java.util.List;

public interface BillRepository {
    void save(Bill bill);
    Bill findById(int billID);
    List<Bill> findAll();
    void update(Bill bill);
    void delete(int billID);
    List<Bill> findByPatientId(int patientID);

}
