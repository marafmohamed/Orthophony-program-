package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class QCM extends Question {

    private List<Integer> reponsesCorrectes;

    public QCM(String enonce, List<String> reponsesPossibles, List<Integer> reponsesCorrectes, int Test_id, int Question_id) {
        super(enonce, reponsesPossibles, 0, Test_id, Question_id);
        if (Question_id > 0) {
            this.reponsesCorrectes = reponsesCorrectes;
        } else {
            try {
                setReponsesCorrectes(reponsesCorrectes);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public List<Integer> getReponsesCorrectes() {
        return this.reponsesCorrectes;
    }
    public void setReponsesCorrectes(List<Integer> reponsesCorrectes) throws SQLException {
        this.reponsesCorrectes = reponsesCorrectes;
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
            pstmt.setString(1, reponsesCorrectes.toString()); // Assuming reponsesCorrectes is stored as a string in the database
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
