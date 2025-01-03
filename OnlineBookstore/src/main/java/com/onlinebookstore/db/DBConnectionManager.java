package com.onlinebookstore.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
@Setter
@NoArgsConstructor
public class DBConnectionManager {
    private String jdbcURL = "jdbc:mysql://localhost:3306/BookstoreDB";
    private String jdbcUsername = "root"; 
    private String jdbcPassword = "password"; 
    private Connection connection;

    public Connection openConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}

