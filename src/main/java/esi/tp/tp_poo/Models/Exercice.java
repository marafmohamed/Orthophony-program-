package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Exercice {
    protected int Exo_id;
    protected String enonce;
    protected String reponse;
    protected String consigne;
    protected int score;

    public Exercice(String enonce, String reponse, String consigne, int Exo_id) {
        this.enonce = enonce;
        this.reponse = reponse;
        this.score = 10;
        this.consigne = consigne;
        if (Exo_id > 0) {
            this.Exo_id = Exo_id;
        } else {
            insertExercice();
        }
    }
    public int getExo_id() {
        return Exo_id;
    }
    public String getEnonce() {
        if (this.enonce != null) {
            return this.enonce;
        } else if (this.Exo_id > 0) {
            // Search for enonce by Exo_id in the database
            this.enonce = fetchDataFromDatabase("enonce", "Exercice", this.Exo_id);
        }
        return null;
    }

    public String getReponse() {
        if (this.reponse != null) {
            return this.reponse;
        } else if (this.Exo_id > 0) {
            this.reponse = fetchDataFromDatabase("reponse", "Exercice", this.Exo_id);
        }
        return null;
    }

    public String getConsigne() {
        if (this.consigne != null) {
            return this.consigne;
        } else if (this.Exo_id > 0) {
            this.consigne = fetchDataFromDatabase("consigne", "Exercice", this.Exo_id);
        }
        return null;
    }

    public int getScore() {
        if (this.score > 0) {
            return this.score;
        } else if (this.Exo_id > 0) {
            String scoreStr = fetchDataFromDatabase("score", "Exercice", this.Exo_id);
            if (scoreStr != null) {
                this.score = Integer.parseInt(scoreStr);
                return this.score;
            }
        }
        return 0;
    }

    private String fetchDataFromDatabase(String columnName, String tableName, int id) {
        try (Connection connection = ConnectDB.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT " + columnName + " FROM " + tableName + " WHERE Exo_id = ?")) {

            pstmt.setInt(1, id);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(columnName);
                } else {
                    throw new SQLException(tableName + " with ID " + id + " not found.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching data from database: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void setEnonce(String enonce) throws SQLException {
        this.enonce = enonce;
        updateAttributeInDatabase("enonce", enonce);
    }

    public void setReponse(String reponse) throws SQLException {
        this.reponse = reponse;
        updateAttributeInDatabase("reponse", reponse);
    }

    public void setConsigne(String consigne) throws SQLException {
        this.consigne = consigne;
        updateAttributeInDatabase("consigne", consigne);
    }

    public void setScore(int score) throws SQLException {
        this.score = score;
        updateAttributeInDatabase("score", String.valueOf(score));
    }

    protected void updateAttributeInDatabase(String columnName, String value) throws SQLException {
        if (this.Exo_id <= 0) {
            throw new IllegalArgumentException("Exo_id is not valid. Unable to update database.");
        }

        try (Connection connection = ConnectDB.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement("UPDATE Exercice SET " + columnName + " = ? WHERE Exo_id = ?")) {

            pstmt.setString(1, value);
            pstmt.setInt(2, this.Exo_id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows were updated for Exo_id " + this.Exo_id);
            }

            System.out.println(columnName + " updated in the database successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating " + columnName + " in database: " + e.getMessage());
            throw e;
        }
    }

    public void insertExercice() {
        try (Connection connection = ConnectDB.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(
                     "INSERT INTO Exercice (enonce, reponse, consigne, score) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, this.enonce);
            pstmt.setString(2, this.reponse);
            pstmt.setString(3, this.consigne);
            pstmt.setInt(4, this.score);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.Exo_id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating Exercice failed, no ID obtained.");
                }
            }

            System.out.println("Exercice added to the database successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting Exercice: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static Exercice getExerciceById(int id) throws SQLException {
        try (Connection connection = ConnectDB.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Exercice WHERE Exo_id = ?")) {

            pstmt.setInt(1, id);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int Exo_id = resultSet.getInt("Exo_id");
                    String enonce = resultSet.getString("enonce");
                    String reponse = resultSet.getString("reponse");
                    String consigne = resultSet.getString("consigne");
                    String material = resultSet.getString("Material");
                    if(material!=null){
                        return new ExerciceMateriel(enonce,reponse,material,consigne,Exo_id);
                    }
                    return new Exercice(enonce, reponse, consigne, Exo_id);
                } else {
                    throw new SQLException("Exercice with ID " + id + " not found.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Exercice from database: " + e.getMessage());
            throw e;
        }
    }
    public static void deleteExerciceById(int id) throws SQLException {
        try (Connection connection = ConnectDB.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement("DELETE FROM Exercice WHERE Exo_id = ?")) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No Exercice found with ID " + id + ". Nothing deleted.");
            }

            System.out.println("Exercice with ID " + id + " deleted from the database.");
        } catch (SQLException e) {
            System.err.println("Error deleting Exercice from database: " + e.getMessage());
            throw e;
        }
    }

}
