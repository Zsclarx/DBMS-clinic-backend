package com.example.demo1.repository;

import com.example.demo1.model.WeeklyStatistics;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Repository
public class WeeklyStatisticsRepositoryImpl implements WeeklyStatisticsRepository {

    private final DataSource dataSource;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public WeeklyStatisticsRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<WeeklyStatistics> getCurrentWeekStatistics() {
        String currentWeek = LocalDate.now().format(formatter);

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM Weekly_Statistics WHERE Week = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, currentWeek);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        WeeklyStatistics stats = new WeeklyStatistics();
                        stats.setStatID(resultSet.getInt("StatID"));
                        stats.setWeek(resultSet.getString("Week"));
                        stats.setTotalAppointments(resultSet.getInt("TotalAppointments"));
                        stats.setTotalIncome(resultSet.getDouble("TotalIncome"));
                        stats.setTotalUsersRegistered(resultSet.getInt("TotalUsersRegistered"));
                        return Optional.of(stats);
                    } else {
                        // If no record for the current week, reset stats to create one
                        resetWeeklyStatistics();
                        // Re-run the query to fetch the newly inserted record
                        try (ResultSet newResultSet = statement.executeQuery()) {
                            if (newResultSet.next()) {
                                WeeklyStatistics newStats = new WeeklyStatistics();
                                newStats.setStatID(newResultSet.getInt("StatID"));
                                newStats.setWeek(newResultSet.getString("Week"));
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
        String currentWeek = LocalDate.now().format(formatter);

        try (Connection connection = dataSource.getConnection()) {
            if (getCurrentWeekStatistics().isEmpty()) {
                resetWeeklyStatistics();
            }
            String updateQuery = "UPDATE Weekly_Statistics SET TotalUsersRegistered = TotalUsersRegistered + 1 WHERE Week = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, currentWeek);
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void incrementIncome(double amount) {
        String currentWeek = LocalDate.now().format(formatter);

        try (Connection connection = dataSource.getConnection()) {
            if (getCurrentWeekStatistics().isEmpty()) {
                resetWeeklyStatistics();
            }
            String updateQuery = "UPDATE Weekly_Statistics SET TotalIncome = TotalIncome + ? WHERE Week = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setDouble(1, amount);
                updateStatement.setString(2, currentWeek);
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void incrementAppointments() {
        String currentWeek = LocalDate.now().format(formatter);

        try (Connection connection = dataSource.getConnection()) {
            if (getCurrentWeekStatistics().isEmpty()) {
                resetWeeklyStatistics();
            }
            String updateQuery = "UPDATE Weekly_Statistics SET TotalAppointments = TotalAppointments + 1 WHERE Week = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, currentWeek);
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetWeeklyStatistics() {
        String currentWeek = LocalDate.now().format(formatter);

        try (Connection connection = dataSource.getConnection()) {
            String deleteQuery = "DELETE FROM Weekly_Statistics WHERE Week <> ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.setString(1, currentWeek);
                deleteStatement.executeUpdate();
            }

            String insertQuery = "INSERT INTO Weekly_Statistics (Week, TotalAppointments, TotalIncome, TotalUsersRegistered) VALUES (?, 0, 0, 0)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, currentWeek);
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
