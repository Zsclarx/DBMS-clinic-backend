package com.example.demo1.service;

import com.example.demo1.model.Bill;
import com.example.demo1.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Override
    public void save(Bill bill) {
        if (bill == null) {
            throw new IllegalArgumentException("Bill cannot be null");
        }
        billRepository.save(bill);
    }

    @Override
    public Bill findById(int billID) {
        return billRepository.findById(billID);
    }

    @Override
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public void update(Bill bill) {
        if (bill == null || bill.getBillID() <= 0) {
            throw new IllegalArgumentException("Invalid bill or bill ID");
        }
        billRepository.update(bill);
    }

    @Override
    public void delete(int billID) {
        if (billID <= 0) {
            throw new IllegalArgumentException("Invalid bill ID");
        }
        billRepository.delete(billID);
    }

    @Override
    public List<Bill> findByPatientId(int patientID) {
        if (patientID <= 0) {
            return Collections.emptyList(); // Return an empty list for invalid patientID
        }
        List<Bill> bills = billRepository.findByPatientId(patientID);
        return (bills != null) ? bills : Collections.emptyList(); // Handle null values
    }
}
