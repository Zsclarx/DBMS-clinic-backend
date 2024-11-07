package com.example.demo1.repository;

import com.example.demo1.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Department> rowMapper = new RowMapper<Department>() {
        @Override
        public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
            Department department = new Department();
            department.setDepartmentID(rs.getInt("departmentID"));
            department.setDepartmentName(rs.getString("departmentName"));
            department.setDescription(rs.getString("description"));
            return department;
        }
    };

    @Override
    public boolean existsByNameOrDescription(String name, String description) {
        String sql = "SELECT COUNT(*) FROM departments WHERE departmentName = ? OR description = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{name, description}, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public void save(Department department) {
        if (department == null || department.getDepartmentName() == null) {
            throw new IllegalArgumentException("Department or Department Name cannot be null");
        }
        String sql = "INSERT INTO departments (departmentName, description) VALUES (?, ?)";
        jdbcTemplate.update(sql, department.getDepartmentName(), department.getDescription());
    }

    @Override
    public Department findById(int departmentID) {
        String sql = "SELECT * FROM departments WHERE departmentID = ?";
        // Using a try-catch to handle possible exceptions if the department does not exist
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{departmentID}, rowMapper);
        } catch (Exception e) {
            return null; // or handle appropriately
        }
    }

    @Override
    public List<Department> findAll() {
        String sql = "SELECT * FROM departments";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void update(Department department) {
        if (department == null || department.getDepartmentID() <= 0) {
            throw new IllegalArgumentException("Department or Department ID cannot be null or invalid");
        }
        String sql = "UPDATE departments SET departmentName = ?, description = ? WHERE departmentID = ?";
        jdbcTemplate.update(sql, department.getDepartmentName(), department.getDescription(), department.getDepartmentID());
    }

    @Override
    public void delete(int departmentID) {
        if (departmentID <= 0) {
            throw new IllegalArgumentException("Department ID cannot be null or invalid");
        }
        String sql = "DELETE FROM departments WHERE departmentID = ?";
        jdbcTemplate.update(sql, departmentID);
    }


}