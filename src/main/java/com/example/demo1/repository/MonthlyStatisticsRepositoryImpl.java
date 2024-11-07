package com.example.demo1.repository;

import com.example.demo1.model.MonthlyStatistics;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Repository
public class MonthlyStatisticsRepositoryImpl implements MonthlyStatisticsRepository {

    private final DataSource dataSource;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM-yyyy");

    public MonthlyStatisticsRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<MonthlyStatistics> getCurrentMonthStatistics() {
        String currentMonth = LocalDate.now().format(formatter);

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM Monthly_Statistics WHERE MonthName = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, currentMonth);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        MonthlyStatistics stats = new MonthlyStatistics();
                        stats.setStatID(resultSet.getInt("StatID"));
                        stats.setMonthName(resultSet.getString("MonthName"));
                        stats.setTotalAppointments(resultSet.getInt("TotalAppointments"));
                        stats.setTotalIncome(resultSet.getDouble("TotalIncome"));
                        stats.setTotalUsersRegistered(resultSet.getInt("TotalUsersRegistered"));
                        return Optional.of(stats);
                    } else {
                        // No record for the current month, reset to create a new entry
                        resetMonthlyStatistics();
                        // Re-run the query to fetch the newly inserted record
                        try (ResultSet newResultSet = statement.executeQuery()) {
                            if (newResultSet.next()) {
                                MonthlyStatistics newStats = new MonthlyStatistics();
                                newStats.setStatID(newResultSet.getInt("StatID"));
                                newStats.setMonthName(newResultSet.getString("MonthName"));
                                newStats.setTotalAppointments(newResultSet.getInt("TotalAppointments"));
                                newStats.setTotalIncome(newResultSet.getDouble("TotalIncome"));
                                newStats.setTotalUsersRegistered(newResultSet.getInt("TotalUsersRegistered"));
                                return Optional.of(newStats);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void incrementUserCount() {
        String currentMonth = LocalDate.now().format(formatter);

        try (Connection connection = dataSource.getConnection()) {
            if (getCurrentMonthStatistics().isEmpty()) {
                resetMonthlyStatistics();
            }
            String updateQuery = "UPDATE Monthly_Statistics SET TotalUsersRegistered = TotalUsersRegistered + 1 WHERE MonthName = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, currentMonth);
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void incrementIncome(double amount) {
        String currentMonth = LocalDate.now().format(formatter);

        try (Connection connection = dataSource.getConnection()) {
            if (getCurrentMonthStatistics().isEmpty()) {
                resetMonthlyStatistics();
            }
            String updateQuery = "UPDATE Monthly_Statistics SET TotalIncome = TotalIncome + ? WHERE MonthName = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setDouble(1, amount);
                updateStatement.setString(2, currentMonth);
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void incrementAppointments() {
        String currentMonth = LocalDate.now().format(formatter);

        try (Connection connection = dataSource.getConnection()) {
            if (getCurrentMonthStatistics().isEmpty()) {
                resetMonthlyStatistics();
            }
            String updateQuery = "UPDATE Monthly_Statistics SET TotalAppointments = TotalAppointments + 1 WHERE MonthName = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, currentMonth);
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetMonthlyStatistics() {
        String currentMonth = LocalDate.now().format(formatter);

        try (Connection connection = dataSource.getConnection()) {
            String deleteQuery = "DELETE FROM Monthly_Statistics WHERE MonthName <> ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.setString(1, currentMonth);
                deleteStatement.executeUpdate();
            }

            String insertQuery = "INSERT INTO Monthly_Statistics (MonthName, TotalAppointments, TotalIncome, TotalUsersRegistered) VALUES (?, 0, 0, 0)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, currentMonth);
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
