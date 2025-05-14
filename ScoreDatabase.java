import java.sql.*;

public class ScoreDatabase {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/pacman";
    private static final String USER = "root";
    private static final String PASS = "1234509876";

    // CREATE
    public static void saveHighScore(String playerName, int score) {
        String query = "INSERT INTO high_scores (player_name, score) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, playerName);
            stmt.setInt(2, score);
            stmt.executeUpdate();

            System.out.println("Score saved successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // READ: Get Highest Score
    public static int getHighestScore() {
        String query = "SELECT MAX(score) FROM high_scores";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    // READ: Get All Scores
    public static void getAllScores() {
        String query = "SELECT * FROM high_scores ORDER BY score DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("All High Scores:");
            while (rs.next()) {
                System.out.println("Player: " + rs.getString("player_name") +
                        ", Score: " + rs.getInt("score"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // UPDATE: Update score by player name
    public static void updateScore(String playerName, int newScore) {
        String query = "UPDATE high_scores SET score = ? WHERE player_name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, newScore);
            stmt.setString(2, playerName);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Score updated for " + playerName);
            } else {
                System.out.println("No record found for player: " + playerName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // DELETE: Delete score by player name
    public static void deleteScore(String playerName) {
        String query = "DELETE FROM high_scores WHERE player_name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, playerName);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Deleted score for player: " + playerName);
            } else {
                System.out.println("No record found for player: " + playerName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
