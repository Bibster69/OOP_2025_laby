package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private java.sql.Connection connection;


    public Connection getConnection() {
        return this.connection;
    }

    public void connect(String dbPath) throws SQLException {
        try {
            String url = "jdbc:sqlite:" + dbPath;
            this.connection = DriverManager.getConnection(url);
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            System.out.println("An error occurred while connecting to the DB " + e.getMessage());
        }
    }

    public void disconnect() throws SQLException {
        try {
            if(this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                System.out.println("Database connection is closed.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while disconnecting from the DB " + e.getMessage());
        }
    }

}
