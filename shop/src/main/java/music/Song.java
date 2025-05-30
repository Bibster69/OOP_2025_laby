package music;

import database.DatabaseConnection;
import java.sql.*;
import java.util.Optional;

public record Song(String artist, String title, int duration) {
    public static class Persistence {
        private static DatabaseConnection dbConnection;

        public static void setConnection(DatabaseConnection connection) {
            dbConnection = connection;
        }

        public static Optional<Song> read(int index) {
            String sql = "SELECT artist, title, length FROM song WHERE id = ?";
            try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql)) {
                stmt.setInt(1, index);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return Optional.of(new Song(
                            rs.getString("artist"),
                            rs.getString("title"),
                            rs.getInt("length")
                    ));
                } else {
                    return Optional.empty();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}