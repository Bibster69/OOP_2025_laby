package auth;

import database.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class AccountManager {
    private final Connection connection;


    public AccountManager(Connection connection) {
        this.connection = connection;
        initializeTable();
    }

    private void initializeTable() {
        try (PreparedStatement stmt = this.connection.prepareStatement("""
                        CREATE TABLE IF NOT EXISTS accounts (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            username TEXT UNIQUE NOT NULL,
                            password TEXT NOT NULL
                        );
                """)) {
            stmt.execute();
        } catch (SQLException e) {
            System.err.println("accounts table creation error: " + e.getMessage());
        }
    }

    public boolean authenticate(String username, String password) {
        try (PreparedStatement stmt = this.connection.prepareStatement(
                "SELECT password FROM accounts WHERE username = ?;")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashed = rs.getString("password");
                return BCrypt.checkpw(password, hashed);
            }
        } catch (SQLException e) {
            System.err.println("Auth error: " + e.getMessage());
        }
        return false;
    }

    public Account getAccount(String usernameOrId) {
        try {
            PreparedStatement stmt;
            boolean isNumeric = usernameOrId.matches("\\d+");

            if (isNumeric) {
                stmt = this.connection.prepareStatement("SELECT id, username FROM accounts WHERE id = ?;");
                stmt.setInt(1, Integer.parseInt(usernameOrId));
            } else {
                stmt = this.connection.prepareStatement("SELECT id, username FROM accounts WHERE username = ?;");
                stmt.setString(1, usernameOrId);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("id"), rs.getString("username"));
            }
        } catch (SQLException e) {
            System.err.println("Accont retrival error: " + e.getMessage());
        }
        return null;
    }

}
