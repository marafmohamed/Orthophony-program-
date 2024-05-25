package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientExoScore extends PatientScore {
    private int exercice;

    public PatientExoScore(int score, int CompteRendu,int exercice , int PatientScore_id) {
        super(score,CompteRendu);
        this.exercice = exercice;
        if(PatientScore_id>0){
            this.PatientScore_id=PatientScore_id;
        }else {
            inserer();
        }
    }

    public Exercice getExercice() {
        try {
            return Exercice.getExerciceById(exercice);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Method to insert PatientExoScore into the Scores table
    public void inserer() {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO Scores (Score, Exo, CompteRendu_id) VALUES (?, ?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, this.getScore());
            pstmt.setInt(2, this.exercice);
            pstmt.setInt(3, this.CompteRendu);
            pstmt.executeUpdate();

            // Retrieve the generated keys (in this case, just one)
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.PatientScore_id = generatedKeys.getInt(1); // Assuming the ID is in the first column
            } else {
                throw new SQLException("Creating PatientExoScore failed, no ID obtained.");
            }

            System.out.println("PatientExoScore inserted into the Scores table successfully. PatientScore ID: " + this.PatientScore_id);
        } catch (SQLException e) {
            System.err.println("Error inserting PatientExoScore: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to update PatientExoScore in the Scores table
    public void update() {
        if (this.PatientScore_id <= 0) {
            throw new IllegalArgumentException("PatientScore_id is not valid. Unable to update database.");
        }

        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "UPDATE Scores SET score = ?, Exo_id = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, this.getScore());
            pstmt.setInt(2, this.exercice);
            pstmt.setInt(3, this.PatientScore_id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows were updated for id " + this.PatientScore_id);
            }

            System.out.println("PatientExoScore updated in the Scores table successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating PatientExoScore: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to delete PatientExoScore from the Scores table
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

            System.out.println("PatientExoScore deleted from the Scores table successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting PatientExoScore: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static List<PatientExoScore> getAllByCompteRendu(int compteRenduId) throws SQLException {
        List<PatientExoScore> scores = new ArrayList<>();

        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Scores WHERE CompteRendu_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, compteRenduId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    int score = resultSet.getInt("Score");
                    int exerciceId = resultSet.getInt("Exo_id");
                    int patientScoreId = resultSet.getInt("id");

                    PatientExoScore patientExoScore = new PatientExoScore(score, compteRenduId, exerciceId, patientScoreId);
                    scores.add(patientExoScore);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching PatientExoScores: " + e.getMessage());
            throw e;
        }

        return scores;
    }
}
