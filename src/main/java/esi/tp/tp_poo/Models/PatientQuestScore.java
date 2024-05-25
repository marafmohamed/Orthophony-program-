package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientQuestScore extends PatientScore {
    private int question; // Assuming this is the ID of the Question
    private Question questionInstance; // To hold the Question object

    public PatientQuestScore(int score, int question, int CompteRendu, int PatientScore_id) {
        super(score, CompteRendu);
        this.question = question;
        this.PatientScore_id = PatientScore_id;
    }

    public Question getQuestion() {
        if (questionInstance == null) {
            try {
                questionInstance = Question.getQuestionById(this.question);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return questionInstance;
    }

    // Method to insert PatientQuestScore into the Scores table
    public void inserer() {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO Scores (score, question, CompteRendu) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, this.getScore());
            pstmt.setInt(2, this.question);
            pstmt.setInt(3, this.CompteRendu);
            pstmt.executeUpdate();

            // Retrieve the generated keys (in this case, just one)
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.PatientScore_id = generatedKeys.getInt(1); // Assuming the ID is in the first column
            } else {
                throw new SQLException("Creating PatientQuestScore failed, no ID obtained.");
            }

            System.out.println("PatientQuestScore inserted into the Scores table successfully. PatientScore ID: " + this.PatientScore_id);
        } catch (SQLException e) {
            System.err.println("Error inserting PatientQuestScore: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to update PatientQuestScore in the Scores table
    public void update() {
        if (this.PatientScore_id <= 0) {
            throw new IllegalArgumentException("PatientScore_id is not valid. Unable to update database.");
        }

        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "UPDATE Scores SET score = ?, question = ?, CompteRendu = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, this.getScore());
            pstmt.setInt(2, this.question);
            pstmt.setInt(3, this.CompteRendu);
            pstmt.setInt(4, this.PatientScore_id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows were updated for id " + this.PatientScore_id);
            }

            System.out.println("PatientQuestScore updated in the Scores table successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating PatientQuestScore: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to delete PatientQuestScore from the Scores table
    public void delete() {
        if (this.PatientScore_id <= 0) {
            throw new IllegalArgumentException("PatientScore_id is not valid. Unable to delete from database.");
        }

        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "DELETE FROM Scores WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, this.PatientScore_id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows were deleted for id " + this.PatientScore_id);
            }

            System.out.println("PatientQuestScore deleted from the Scores table successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting PatientQuestScore: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static List<PatientQuestScore> getAllByCompteRendu(int compteRenduId) throws SQLException {
        List<PatientQuestScore> scores = new ArrayList<>();

        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Scores WHERE CompteRendu = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, compteRenduId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    int score = resultSet.getInt("score");
                    int questionId = resultSet.getInt("question");
                    int patientScoreId = resultSet.getInt("id");

                    PatientQuestScore patientQuestScore = new PatientQuestScore(score, questionId, compteRenduId, patientScoreId);
                    scores.add(patientQuestScore);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching PatientQuestScores: " + e.getMessage());
            throw e;
        }

        return scores;
    }
}
