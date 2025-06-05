package music;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite driver loaded!");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
        }

        // Zad.1

        DatabaseConnection db = new DatabaseConnection();

        try {
            // można użyć nazwy pliku "test.db"
            db.connect("test.db");
            Connection conn = db.getConnection();

            Statement stmt = conn.createStatement();

            // 1. Tworzymy tabele
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT)");

            // 2. Dodajemy 2 rzedy
            stmt.executeUpdate("INSERT INTO users (name) VALUES ('Alice')");
            stmt.executeUpdate("INSERT INTO users (name) VALUES ('Bob')");

            // 3. Czytamy te rzedy
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            // 4. Wypisujemy je
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println("User ID: " + id + ", Name: " + name);
            }

            rs.close();
            stmt.close();
            db.disconnect();

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

//        DatabaseConnection db = new DatabaseConnection();
//        try {
//            db.connect("accounts.db");
//            Connection conn = db.getConnection();
//
//            AccountManager accountManager = new AccountManager(conn);
//
//            // Rejestrujemy użytkownika
//            System.out.println("\n📥 Rejestracja:");
//            accountManager.register("alice", "password123");
//            accountManager.register("bob", "securePass");
//
//            // Auth
//            System.out.println("\n🔐 Testowanie logowania:");
//            System.out.println("alice/password123: " + accountManager.authenticate("alice", "password123")); // true
//            System.out.println("bob/wrongPass: " + accountManager.authenticate("bob", "wrongPass")); // false
//
//            // Pobieramy konto za pomocą nazwy użytkownika
//            System.out.println("\n📄 Pobieranie konta:");
//            Account acc = accountManager.getAccount("alice");
//            if (acc != null) {
//                System.out.println("Znaleziono konto: id=" + acc.id() + ", username=" + acc.username());
//            }
//
//            // to samy za pomocą id
//            if (acc != null) {
//                Account accById = accountManager.getAccount(String.valueOf(acc.id()));
//                if (accById != null) {
//                    System.out.println("Z konta ID: id=" + accById.id() + ", username=" + accById.username());
//                }
//            }
//
//            db.disconnect();
//
//        } catch (SQLException e) {
//            System.err.println("Błąd bazy danych: " + e.getMessage());
//        }
    }
}