package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class QCU extends Question {

    private int reponseCorrecte;

    public QCU(String enonce, ArrayList<String> propositions, int reponse,int Test_id,int Question_id) {
        super(enonce, propositions, 0, Test_id, Question_id);
        if (Question_id > 0) {
            this.reponseCorrecte = reponse;
        } else {
            try {
                setReponseCorrecte(reponse);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public int getReponseCorrecte() {
        return this.reponseCorrecte;
    }
    public void setReponseCorrecte(int reponsesCorrectes) throws SQLException {
        this.reponseCorrecte = reponsesCorrectes;
        updateReponsesCorrectesInDatabase();
    }

    // Update reponsesCorrectes attribute in the database
    private void updateReponsesCorrectesInDatabase() throws SQLException {
        if (this.Question_id <= 0) {
            throw new IllegalArgumentException("Question_id is not valid. Unable to update database.");
        }

        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "UPDATE Question SET Reponse = ? WHERE Question_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(reponseCorrecte)); // Assuming reponsesCorrectes is stored as a string in the database
            pstmt.setInt(2, this.Question_id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows were updated for Question_id " + this.Question_id);
            }

            System.out.println("reponsesCorrectes updated in the database successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating reponsesCorrectes in database: " + e.getMessage());
            throw e;
        }
    }
}
