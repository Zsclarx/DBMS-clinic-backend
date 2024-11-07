package com.example.demo1.service;

import com.example.demo1.dto.AuthResponse;
import com.example.demo1.dto.LoginRequest;
import com.example.demo1.model.User;
import com.example.demo1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtservice;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // Initialize the password encoder here
    }

    @Override
    public User findById(int userID) {
        try {
            return userRepository.findById(userID);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by ID", e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return userRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all users", e);
        }
    }

    @Override
    public User save(User user) {
        try {
            if (usernameExists(user.getUsername())) {
                throw new IllegalArgumentException("Username already exists.");
            }

            // Encode the password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Save the user and return the saved user
            return userRepository.save(user);
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user", e);
        }
    }
    @Override
    public boolean usernameExists(String username) throws SQLException {
        return userRepository.usernameExists(username);
    }

    @Override
    public void update(User user) throws SQLException {
        userRepository.update(user);
    }

    @Override
    public void delete(int userID) {
        try {
            userRepository.delete(userID);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }

    public User authenticate(String username, String password) {
        try {
            User user = userRepository.findByUsername(username);
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                return user; // Authentication successful
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error during authentication", e);
        }
        return null; // Authentication failed
    }

    //    public String verify(User user){
//        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
//
//        if(authentication.isAuthenticated())
//            return  jwtservice.generateToken(user.getUsername());
//
//        return "fail";
//    }
// Inside UserServiceImpl
    public AuthResponse verify(LoginRequest loginRequest) throws SQLException {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // Fetch the complete User from the database using the username
            User user = userRepository.findByUsername(loginRequest.getUsername());
            if (user != null) {
                String token = jwtservice.generateToken(user.getUsername());
                String role = user.getRole(); // Now you can access the role
                String redirectUrl = getRedirectUrlBasedOnRole(role);
                int userID = user.getUserID();
                return new AuthResponse(token, redirectUrl,userID);
            }
        }
        return null; // or throw an exception for failed login
    }

    private String getRedirectUrlBasedOnRole(String role) {
        role = role.toUpperCase();
        return switch (role) {
            case "ADMIN" -> "/admin";
            case "PATIENT" -> "/patients";
            case "DOCTOR" -> "/doctors";
            default -> "/home";
        };
    }

}