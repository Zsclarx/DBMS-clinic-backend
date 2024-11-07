package com.example.demo1.service;

import com.example.demo1.dto.AuthResponse;
import com.example.demo1.dto.LoginRequest;
import com.example.demo1.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    User findById(int userID) throws SQLException;
    List<User> findAll() throws SQLException;
    User save(User user) throws SQLException;
    void update(User user) throws SQLException;
    void delete(int userID) throws SQLException;
    User authenticate(String username, String password);
    public boolean usernameExists(String username) throws SQLException;
    AuthResponse verify(LoginRequest user) throws SQLException;
}
