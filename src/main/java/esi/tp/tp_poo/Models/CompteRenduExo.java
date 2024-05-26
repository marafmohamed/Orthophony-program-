package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompteRenduExo extends CompteRendu {
    private List<PatientExoScore> ExoScores = new ArrayList<>();

    public CompteRenduExo(int IdOrthophoniste, int Test_id, double TotalScore, int patient, int CompteRendu_id) throws SQLException {
        super(IdOrthophoniste, Test_id, TotalScore, patient);
        if (CompteRendu_id < 1) {
            try {
                insertCompteRendu();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            this.CompteRendu_id = CompteRendu_id;
            this.ExoScores=GetExoScores();
        }
    }

    public void AddExoScore(PatientExoScore ExoScore) {
        this.ExoScores.add(ExoScore);
    }
    public List<PatientExoScore> GetExoScores() {
        try {
            return PatientExoScore.getAllByCompteRendu(this.CompteRendu_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public double CalculateTotalScore() {
        if (ExoScores.isEmpty()) return this.TotalScore = 0;
        double totalScore = 0;
        for (PatientExoScore PScore : ExoScores)
            totalScore += (double) PScore.getScore() / PScore.getExercice().getScore();
        return this.TotalScore = totalScore;
    }

    // Database insertion method
    public void insertCompteRendu() throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO CompteRendu (Orthophoniste_id, Test_id, TotalScore, patient) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, this.IdOrthophoniste);
            pstmt.setInt(2, this.Test_id);
            pstmt.setDouble(3, this.TotalScore);
            pstmt.setInt(4, this.patient);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.CompteRendu_id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating CompteRenduExo failed, no ID obtained.");
                }
            }

            System.out.println("CompteRenduExo added to the database successfully. CompteRendu ID: " + this.CompteRendu_id);
        } catch (SQLException e) {
            System.out.println("Error inserting CompteRenduExo: " + e.getMessage());
            throw e;
        }
    }
}
