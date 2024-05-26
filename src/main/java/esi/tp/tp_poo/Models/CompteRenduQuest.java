package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompteRenduQuest extends CompteRendu {
    private List<PatientQuestScore> QuestScores = new ArrayList<>();

    public CompteRenduQuest(int IdOrthophoniste, int Test_id, double TotalScore, int patient, int CompteRendu_id) throws SQLException {
        super(IdOrthophoniste, Test_id, TotalScore, patient);
        if (CompteRendu_id < 1) {
            try {
                insertCompteRendu();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            this.CompteRendu_id = CompteRendu_id;
            this.QuestScores = getQuestScores();
        }
    }

    public void addQuestScore(PatientQuestScore QuestScore) {
        this.QuestScores.add(QuestScore);
    }

    public List<PatientQuestScore> getQuestScores() {
        try {
            return PatientQuestScore.getAllByCompteRendu(this.CompteRendu_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double CalculateTotalScore() {
        if (QuestScores.isEmpty()) return this.TotalScore = 0;
        double totalScore = 0;
        for (PatientQuestScore questScore : QuestScores) {
            totalScore += questScore.getScore();
        }
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
                    throw new SQLException("Creating CompteRenduQuest failed, no ID obtained.");
                }
            }

            System.out.println("CompteRenduQuest added to the database successfully. CompteRendu ID: " + this.CompteRendu_id);
        } catch (SQLException e) {
            System.out.println("Error inserting CompteRenduQuest: " + e.getMessage());
            throw e;
        }
    }
}
