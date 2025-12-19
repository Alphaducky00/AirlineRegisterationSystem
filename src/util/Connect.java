package util;

import java.sql.*;

public class Connect {
    
    public java.sql.Connection connection;
    
    private static final String url = "jdbc:mysql://localhost:3306/airline";
    private static final String username = "root";
    private static final String password = "";
    
    public void connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean login(String username, String password) {
        String query = "SELECT * FROM login WHERE username = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public int getFlightCount() {
        String queryString = "SELECT COUNT(*) AS total FROM flights";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryString);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch flights: " + e.getMessage());
        }
        return 0;
    }

    public int getBookingCount() {
        String queryString = "SELECT COUNT(*) AS total FROM booking";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryString);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch bookings: " + e.getMessage());
        }
        return 0;
    }

    public int getUsersCount() {
        String queryString = "SELECT COUNT(*) AS total FROM users";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryString);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch users: " + e.getMessage());
        }
        return 0;
    }
    
    public java.sql.Connection getConnection() {
        return connection;
    }
}
