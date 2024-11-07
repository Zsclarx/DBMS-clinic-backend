package com.example.demo1.controller;

import com.example.demo1.model.User;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.findAll();
            return ResponseEntity.ok(users);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (IllegalArgumentException e) {
            // Catch the specific exception for duplicate username and return a 409 Conflict status
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        try {
            User existingUser = userService.findById(id);
            if (existingUser == null) {
                return ResponseEntity.notFound().build();
            }

            if (user.getUsername() != null) existingUser.setUsername(user.getUsername());
            if (user.getPassword() != null) existingUser.setPassword(user.getPassword());
            if (user.getRole() != null) existingUser.setRole(user.getRole());
            if (user.getContactInfo() != null) existingUser.setContactInfo(user.getContactInfo());
            if (user.getYearOfBirth() != 0) existingUser.setYearOfBirth(user.getYearOfBirth());
            if (user.getMonthOfBirth() != 0) existingUser.setMonthOfBirth(user.getMonthOfBirth());
            if (user.getDayOfBirth() != 0) existingUser.setDayOfBirth(user.getDayOfBirth());
            if (user.getBloodGroup() != null) existingUser.setBloodGroup(user.getBloodGroup());
            if (user.getFirstName() != null) existingUser.setFirstName(user.getFirstName());
            if (user.getLastName() != null) existingUser.setLastName(user.getLastName());
            if (user.getAge() != 0) existingUser.setAge(user.getAge());
            if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
            if (user.getAddress() != null) existingUser.setAddress(user.getAddress());
            if (user.getGender() != null) existingUser.setGender(user.getGender());

            userService.update(existingUser); // Save updated user

            return ResponseEntity.ok(existingUser);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}