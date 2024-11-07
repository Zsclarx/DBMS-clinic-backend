package com.example.demo1.repository;

import com.example.demo1.model.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BillRepositoryImpl implements BillRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Bill> rowMapper = new RowMapper<Bill>() {
        @Override
        public Bill mapRow(ResultSet rs, int rowNum) throws SQLException {
            Bill bill = new Bill();
            bill.setBillID(rs.getInt("billID"));
            bill.setPatientID(rs.getInt("patientID"));
            bill.setAppointmentID(rs.getInt("appointmentID"));
            bill.setAmount(rs.getDouble("amount"));
            bill.setDateIssued(rs.getDate("dateIssued"));
            bill.setTransactionID(rs.getString("transactionID"));
            return bill;
        }
    };

    @Override
    public void save(Bill bill) {
        String sql = "INSERT INTO bills (patientID, appointmentID, amount, dateIssued, transactionID) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, bill.getPatientID(), bill.getAppointmentID(),
                bill.getAmount(), bill.getDateIssued(),
                bill.getTransactionID());
    }

    @Override
    public Bill findById(int billID) {
        String sql = "SELECT * FROM bills WHERE billID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{billID}, rowMapper);
    }

    @Override
    public List<Bill> findAll() {
        String sql = "SELECT * FROM bills";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void update(Bill bill) {
        String sql = "UPDATE bills SET patientID = ?, appointmentID = ?, amount = ?, dateIssued = ?, transactionID = ? WHERE billID = ?";
        jdbcTemplate.update(sql, bill.getPatientID(), bill.getAppointmentID(),
                bill.getAmount(), bill.getDateIssued(),
                bill.getTransactionID(), bill.getBillID());
    }

    @Override
    public void delete(int billID) {
        String sql = "DELETE FROM bills WHERE billID = ?";
        jdbcTemplate.update(sql, billID);
    }

    @Override
    public List<Bill> findByPatientId(int patientID) {
        if (patientID <= 0) { // Validate patientID
            return new ArrayList<>(); // Return empty list for invalid ID
        }

        String sql = "SELECT * FROM bills WHERE patientID = ?";
        List<Bill> bills = jdbcTemplate.query(sql, new Object[]{patientID}, rowMapper);

        return (bills != null) ? bills : new ArrayList<>(); // Handle null values
    }
}
