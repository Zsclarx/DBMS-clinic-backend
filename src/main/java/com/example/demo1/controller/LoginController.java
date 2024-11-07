package com.example.demo1.controller;

import com.example.demo1.dto.AuthResponse;
import com.example.demo1.dto.LoginRequest;
import com.example.demo1.model.User;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@CrossOrigin

@RequestMapping("/auth")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        try {
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
        catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
//        try {
//            User user = userService.authenticate(username, password);
//            if (user != null) {
//                String role = user.getRole();
//                String redirectUrl = getRedirectUrlBasedOnRole(role);
//                return ResponseEntity.ok(redirectUrl); // Send URL to redirect based on role
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed");
//        }
//    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest user) throws SQLException {

        return userService.verify(user);
    }

    private String getRedirectUrlBasedOnRole(String role) {
        switch (role) {
            case "ADMIN":
                return "/admin-dashboard";
            case "PATIENT":
                return "/";
            case "DOCTOR":
                return "/doctor-dashboard";
            default:
                return "/home";
        }
    }
}
