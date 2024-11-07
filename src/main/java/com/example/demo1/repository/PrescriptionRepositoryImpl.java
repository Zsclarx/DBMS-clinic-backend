package com.example.demo1.repository;

import com.example.demo1.model.Prescription;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PrescriptionRepositoryImpl implements PrescriptionRepository {
    private final DataSource dataSource;

    public PrescriptionRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String INSERT_PRESCRIPTION_SQL = "INSERT INTO prescriptions (appointmentID, dosage) VALUES (?, ?)";
    private static final String SELECT_PRESCRIPTION_BY_ID = "SELECT prescriptionID, appointmentID, dosage FROM prescriptions WHERE prescriptionID = ?";
    private static final String SELECT_ALL_PRESCRIPTIONS = "SELECT * FROM prescriptions";
    private static final String SELECT_PRESCRIPTIONS_BY_PATIENT_ID = "SELECT prescriptionID, appointmentID, dosage FROM prescriptions WHERE appointmentID IN (SELECT appointmentID FROM appointments WHERE appointmentID = ?)"; // Add this line
    private static final String UPDATE_PRESCRIPTION_SQL = "UPDATE prescriptions SET dosage = ? WHERE prescriptionID = ?";
    private static final String DELETE_PRESCRIPTION_SQL = "DELETE FROM prescriptions WHERE prescriptionID = ?";

    @Override
    public void save(Prescription prescription) {
        if (prescription == null) {
            throw new IllegalArgumentException("Prescription cannot be null");
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRESCRIPTION_SQL)) {
            preparedStatement.setInt(1, prescription.getAppointmentID());
            preparedStatement.setString(2, prescription.getDosage());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Prescription findById(int prescriptionID) {
        if (prescriptionID <= 0) {
            throw new IllegalArgumentException("Invalid Prescription ID");
        }

        Prescription prescription = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRESCRIPTION_BY_ID)) {
            preparedStatement.setInt(1, prescriptionID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                prescription = new Prescription();
                prescription.setPrescriptionID(resultSet.getInt("prescriptionID"));
                prescription.setAppointmentID(resultSet.getInt("appointmentID"));
                prescription.setDosage(resultSet.getString("dosage"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescription;
    }

    @Override
    public List<Prescription> findAll() {
        List<Prescription> prescriptions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRESCRIPTIONS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Prescription prescription = new Prescription();
                prescription.setPrescriptionID(resultSet.getInt("prescriptionID"));
                prescription.setAppointmentID(resultSet.getInt("appointmentID"));
                prescription.setDosage(resultSet.getString("dosage"));
                prescriptions.add(prescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptions;
    }

    // New method to find prescriptions by patient ID
    @Override
    public List<Prescription> findByPatientId(int patientID) {
        List<Prescription> prescriptions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRESCRIPTIONS_BY_PATIENT_ID)) {
            preparedStatement.setInt(1, patientID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Prescription prescription = new Prescription();
                prescription.setPrescriptionID(resultSet.getInt("prescriptionID"));
                prescription.setAppointmentID(resultSet.getInt("appointmentID"));
                prescription.setDosage(resultSet.getString("dosage"));
                prescriptions.add(prescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptions;
    }

    @Override
    public void update(Prescription prescription) {
        if (prescription == null || prescription.getPrescriptionID() <= 0) {
            throw new IllegalArgumentException("Prescription or Prescription ID cannot be null or invalid");
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRESCRIPTION_SQL)) {
            preparedStatement.setString(1, prescription.getDosage());
            preparedStatement.setInt(2, prescription.getPrescriptionID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int prescriptionID) {
        if (prescriptionID <= 0) {
            throw new IllegalArgumentException("Invalid Prescription ID");
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRESCRIPTION_SQL)) {
            preparedStatement.setInt(1, prescriptionID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}