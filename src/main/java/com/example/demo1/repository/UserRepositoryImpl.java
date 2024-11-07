package com.example.demo1.repository;

import com.example.demo1.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private final DataSource dataSource;

    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findById(int userID) throws SQLException {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        }
        return null;
    }
    @Override
    public boolean usernameExists(String username) throws SQLException{
        String sql = "SELECT COUNT(*) FROM Users WHERE Username = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Return true if the count is greater than 0
                }
            }
        }
        return false; // Username does not exist
    }
    @Override
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        }
        return users;
    }

    @Override
    public User save(User user) throws SQLException {
        String sql = "INSERT INTO Users (Username, Password, Role, ContactInfo, YearOfBirth, MonthOfBirth, DayOfBirth, BloodGroup, FirstName, LastName, Age, Email, Address, Gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Add RETURN_GENERATED_KEYS

            setUserParameters(stmt, user);
            stmt.executeUpdate();

            // Retrieve the generated keys
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserID(generatedKeys.getInt(1)); // Assuming user ID is the first column
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            return user; // Return the modified user object
        } catch (SQLException e) {
            logger.error("Error saving user: {}, Exception: {}", user.getUsername(), e.getMessage());
            throw e;
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = "UPDATE Users SET Username = ?, Password = ?, Role = ?, ContactInfo = ?, YearOfBirth = ?, MonthOfBirth = ?, DayOfBirth = ?, BloodGroup = ?, FirstName = ?, LastName = ?, Age = ?, Email = ?, Address = ?, Gender = ? WHERE UserID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setUserParameters(stmt, user);
            stmt.setInt(15, user.getUserID());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int userID) throws SQLException {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.executeUpdate();
        }
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM Users WHERE Username = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        }
        return null;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getInt("UserID"));
        user.setUsername(rs.getString("Username"));
        user.setPassword(rs.getString("Password"));
        user.setRole(rs.getString("Role"));
        user.setContactInfo(rs.getString("ContactInfo"));
        user.setYearOfBirth(rs.getInt("YearOfBirth"));
        user.setMonthOfBirth(rs.getInt("MonthOfBirth"));
        user.setDayOfBirth(rs.getInt("DayOfBirth"));
        user.setBloodGroup(rs.getString("BloodGroup"));
        user.setFirstName(rs.getString("FirstName"));
        user.setLastName(rs.getString("LastName"));
        user.setAge(rs.getInt("Age"));
        user.setEmail(rs.getString("Email"));
        user.setAddress(rs.getString("Address"));
        user.setGender(rs.getString("Gender"));
        return user;
    }

    private void setUserParameters(PreparedStatement stmt, User user) throws SQLException {
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getRole());
        stmt.setString(4, user.getContactInfo());
        stmt.setInt(5, user.getYearOfBirth());
        stmt.setInt(6, user.getMonthOfBirth());
        stmt.setInt(7, user.getDayOfBirth());
        stmt.setString(8, user.getBloodGroup());
        stmt.setString(9, user.getFirstName());
        stmt.setString(10, user.getLastName());
        stmt.setInt(11, user.getAge());
        stmt.setString(12, user.getEmail());
        stmt.setString(13, user.getAddress());
        stmt.setString(14, user.getGender());
    }
}