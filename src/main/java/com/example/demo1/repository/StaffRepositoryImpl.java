package com.example.demo1.repository;

import com.example.demo1.model.Staff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StaffRepositoryImpl implements StaffRepository {
    private static final Logger logger = LoggerFactory.getLogger(StaffRepositoryImpl.class);
    private final DataSource dataSource;

    @Autowired
    public StaffRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String INSERT_STAFF_SQL = "INSERT INTO staff (userID, designation, salary) VALUES (?, ?, ?)";
    private static final String SELECT_STAFF_BY_ID = "SELECT staffID, userID, designation, salary FROM staff WHERE staffID = ?";
    private static final String SELECT_ALL_STAFF = "SELECT * FROM staff";
    private static final String UPDATE_STAFF_SQL = "UPDATE staff SET userID = ?, designation = ?, salary = ? WHERE staffID = ?";
    private static final String DELETE_STAFF_SQL = "DELETE FROM staff WHERE staffID = ?";

    @Override
    public void save(Staff staff) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STAFF_SQL)) {
            setStaffParameters(preparedStatement, staff);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error saving staff: {}, Exception: {}", staff.getDesignation(), e.getMessage());
        }
    }

    @Override
    public Staff findById(int staffID) {
        Staff staff = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STAFF_BY_ID)) {
            preparedStatement.setInt(1, staffID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                staff = mapStaff(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Error finding staff by ID: {}, Exception: {}", staffID, e.getMessage());
        }
        return staff;
    }

    @Override
    public List<Staff> findAll() {
        List<Staff> staffList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STAFF);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                staffList.add(mapStaff(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Error finding all staff: {}", e.getMessage());
        }
        return staffList;
    }

    @Override
    public void update(Staff staff) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STAFF_SQL)) {
            setStaffParameters(preparedStatement, staff);
            preparedStatement.setInt(4, staff.getStaffID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error updating staff: {}, Exception: {}", staff.getStaffID(), e.getMessage());
        }
    }

    @Override
    public void delete(int staffID) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STAFF_SQL)) {
            preparedStatement.setInt(1, staffID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting staff with ID: {}, Exception: {}", staffID, e.getMessage());
        }
    }

    private Staff mapStaff(ResultSet rs) throws SQLException {
        Staff staff = new Staff();
        staff.setStaffID(rs.getInt("staffID"));
        staff.setUserID(rs.getInt("userID"));
        staff.setDesignation(rs.getString("designation"));
        staff.setSalary(rs.getDouble("salary"));
        return staff;
    }

    private void setStaffParameters(PreparedStatement stmt, Staff staff) throws SQLException {
        stmt.setInt(1, staff.getUserID());
        stmt.setString(2, staff.getDesignation());
        stmt.setDouble(3, staff.getSalary());
    }
}
