package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestQuestions extends Test {

    private ArrayList<Question> Questionnaire;
    private int scoreTotal;

    public TestQuestions(String nomTest, String capacité, int patient, int compteRendu, Question[] Questionnaire, int Bilan) {
        super(nomTest, capacité, patient, compteRendu, Bilan);
        this.Questionnaire = new ArrayList<Question>();
    }

    public int CalculerScore() {
        for (Question question : Questionnaire) {
            scoreTotal += question.score;
        }
        return scoreTotal;
    }

    public void addQestion(Question question) {
        Questionnaire.add(question);

        // Insert a new row in the Exo_Test table
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO Exo_Test (Exo_id, Test_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, question.getQuestion_id()); // Assuming there's a getExo_id() method in Exercice class
            pstmt.setInt(2, this.Test_id); // Assuming Test_id is set previously in insertTest() method
            pstmt.executeUpdate();

            System.out.println("Exercice added to the array and inserted into the Exo_Test table successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding Exercice: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void insertTest() {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO TestQuestions (nom, Capacite, Patient, CompteRendu) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.nomTest);
            pstmt.setString(2, this.Capacité);
            pstmt.setInt(3, this.patient);
            pstmt.setInt(4, this.compteRendu);
            pstmt.executeUpdate();

            // Retrieve the generated keys (in this case, just one)
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.Test_id = generatedKeys.getInt(1); // Assuming the ID is in the first column
            } else {
                throw new SQLException("Creating TestQuestions failed, no ID obtained.");
            }

            System.out.println("TestExercice added to the database successfully. Test ID: " + this.Test_id);
        } catch (SQLException e) {
            System.out.println("Error inserting TestQuestions: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
