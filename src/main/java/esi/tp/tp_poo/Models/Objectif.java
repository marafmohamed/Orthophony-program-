package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;
import esi.tp.tp_poo.Enums.TypeTerm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Objectif {
    private int id;
    private String nom;
    private String description;
    private TypeTerm terme;
    private int evolution;
    private int ficheSuiviId;

    public Objectif(String nom, String description, TypeTerm terme, int ficheSuiviId, int id) {
        this.nom = nom;
        this.description = description;
        this.terme = terme;
        this.evolution = 0;
        this.ficheSuiviId = ficheSuiviId;
        if (id > 0) {
            this.id = id;
        } else {
            insertObjectif();
        }
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
        updateObjectif();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        updateObjectif();
    }

    public TypeTerm getTerme() {
        return terme;
    }

    public void setTerme(TypeTerm terme) {
        this.terme = terme;
        updateObjectif();
    }


    public int getEvolution() {
        return evolution;
    }

    public int getFicheSuiviId() {
        return ficheSuiviId;
    }

    public void setFicheSuiviId(int ficheSuiviId) {
        this.ficheSuiviId = ficheSuiviId;
    }

    public void evaluer(int evolution) throws IllegalArgumentException {
        if (evolution < 1 || evolution > 5) {
            throw new IllegalArgumentException("Evaluation must be between 1 and 5");
        }
        this.evolution = evolution;
    }

    // Method to insert Objectif into the database
    private void insertObjectif() {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO Objectif (nom, description, terme, evolution, FichSuivi) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.nom);
            pstmt.setString(2, this.description);
            pstmt.setString(3, this.terme.name());
            pstmt.setInt(4, this.evolution);
            pstmt.setInt(5, this.ficheSuiviId);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating Objectif failed, no ID obtained.");
                }
            }

            System.out.println("Objectif added to the database successfully. Objectif ID: " + this.id);
        } catch (SQLException e) {
            System.err.println("Error inserting Objectif: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to update Objectif in the database
    public void updateObjectif() {
        if (this.id <= 0) {
            throw new IllegalArgumentException("Objectif ID is not valid. Unable to update database.");
        }

        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "UPDATE Objectif SET nom = ?, description = ?, terme = ?, evolution = ?, FichSuivi = ? WHERE Objectif_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, this.nom);
            pstmt.setString(2, this.description);
            pstmt.setString(3, this.terme.name());
            pstmt.setInt(4, this.evolution);
            pstmt.setInt(5, this.ficheSuiviId);
            pstmt.setInt(6, this.id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows were updated for id " + this.id);
            }

            System.out.println("Objectif updated in the database successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating Objectif: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to delete Objectif from the database
    public void deleteObjectif() {
        if (this.id <= 0) {
            throw new IllegalArgumentException("Objectif ID is not valid. Unable to delete from database.");
        }

        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "DELETE FROM Objectif WHERE Objectif_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows were deleted for id " + this.id);
            }

            System.out.println("Objectif deleted from the database successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting Objectif: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to get an Objectif by ID
    public static Objectif getObjectifById(int id) throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Objectif WHERE Objectif_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    String nom = resultSet.getString("nom");
                    String description = resultSet.getString("description");
                    TypeTerm terme = TypeTerm.valueOf(resultSet.getString("terme"));
                    int evolution = resultSet.getInt("evolution");
                    int ficheSuiviId = resultSet.getInt("FichSuivi");

                    Objectif objectif = new Objectif(nom, description, terme, ficheSuiviId, id);
                    objectif.evolution = evolution;

                    return objectif;
                } else {
                    throw new SQLException("Objectif with ID " + id + " not found.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Objectif: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Method to get all Objectifs by FicheSuivi ID
    public static List<Objectif> getObjectifsByFicheSuiviId(int ficheSuiviId) throws SQLException {
        List<Objectif> objectifs = new ArrayList<>();

        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Objectif WHERE FichSuivi = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, ficheSuiviId);
            try (ResultSet resultSet = pstmt.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    String description = resultSet.getString("description");
                    TypeTerm terme = TypeTerm.valueOf(resultSet.getString("terme"));
                    int evolution = resultSet.getInt("evolution");

                    Objectif objectif = new Objectif(nom, description, terme, ficheSuiviId, id);
                    objectif.evolution = evolution;

                    objectifs.add(objectif);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all Objectifs: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        return objectifs;
    }
}
