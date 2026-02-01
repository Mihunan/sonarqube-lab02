package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserService {

    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;

    static {
        DB_URL = System.getenv("DB_URL");
        DB_USER = System.getenv("DB_USER");
        DB_PASSWORD = System.getenv("DB_PASSWORD");

        if (DB_URL == null || DB_USER == null || DB_PASSWORD == null) {
            throw new IllegalStateException(
                "Database environment variables (DB_URL, DB_USER, DB_PASSWORD) must be set");
        }
    }

    public void findUser(String username) throws SQLException {

        String query = "SELECT * FROM users WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ps.executeQuery();
        }
    }

    public void deleteUser(String username) throws SQLException {

        String query = "DELETE FROM users WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ps.executeUpdate();
        }
    }
}
