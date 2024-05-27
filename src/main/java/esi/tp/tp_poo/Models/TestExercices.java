package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestExercices extends Test {

    private ArrayList<Exercice> SerieExercices;
    double scoreTotal;
    final boolean exo=true;


    public TestExercices(String nomTest, String capacité, int Test_id) {
        super(nomTest, capacité);
        this.SerieExercices = new ArrayList<Exercice>();
        if (Test_id > 0) {
            this.Test_id = Test_id;
            retrieveExercicesFromDB();
        } else {
            insertTest();
        }
    }

    public double CalculerScore() {
        for (Exercice exercice : SerieExercices) {
            scoreTotal += exercice.getScore();
        }
        return scoreTotal;
    }

    public void addExercice(Exercice exercice) {
        // Add the exercise to the array
        SerieExercices.add(exercice);

        // Insert a new row in the Exo_Test table
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO Exo_Test (Exo_id, Test_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, exercice.getExo_id()); // Assuming there's a getExo_id() method in Exercice class
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

        String sql = "INSERT INTO Test (nom, Capacite ,exo) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.nomTest);
            pstmt.setString(2, this.Capacité);
            pstmt.setBoolean(3,this.exo);
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
    private void retrieveExercicesFromDB() {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT Exo_id FROM Exo_Test WHERE Test_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, this.Test_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int exoId = rs.getInt("Exo_id");
                    Exercice exercice = Exercice.getExerciceById(exoId);
                    if (exercice != null) {
                        SerieExercices.add(exercice);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving exercises: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
