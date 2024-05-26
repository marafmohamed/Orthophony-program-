package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;
import esi.tp.tp_poo.Enums.TypeTrouble;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Trouble {
    private int Trouble_id;
    private String nom;
    private TypeTrouble type;
    private int Bilan;

    // Constructor
    public Trouble(String nom, TypeTrouble type, int Bilan,int Trouble_id) {
        this.nom = nom;
        this.type = type;
        this.Bilan = Bilan;
        if(Trouble_id>0){
            this.Trouble_id=Trouble_id;
        }else {
            try {
                insertTrouble();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Getters and setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
        try {
            updateTrouble();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public TypeTrouble getType() {
        return type;
    }

    public void setType(TypeTrouble type) {
        this.type = type;
        try {
            updateTrouble();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public int getBilan() {
        return Bilan;
    }

    public void setBilan(int Bilan) {
        this.Bilan = Bilan;
        try {
            updateTrouble();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Insert method
    public void insertTrouble() throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO Trouble (nom, type, Bilan) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.nom);
            pstmt.setString(2, this.type.toString());
            pstmt.setInt(3, this.Bilan);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.Trouble_id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating Trouble failed, no ID obtained.");
                }
            }

            System.out.println("Trouble added to the database successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting Trouble: " + e.getMessage());
            throw e;
        }
    }

    // CRUD operations

    // Read by ID
    public static Trouble getTroubleById(int id) throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Trouble WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    TypeTrouble type = TypeTrouble.valueOf(rs.getString("type"));
                    int bilan = rs.getInt("Bilan");

                    return new Trouble(nom, type, bilan,rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching Trouble: " + e.getMessage());
            throw e;
        }
        return null;
    }

    // Update
    public void updateTrouble() throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "UPDATE Trouble SET nom = ?, type = ?, Bilan = ? WHERE Trouble_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, this.nom);
            pstmt.setString(2, this.type.toString());
            pstmt.setInt(3, this.Bilan);
            pstmt.setInt(4, this.Trouble_id); // Assuming there is an id attribute or method to get it

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows were updated for Trouble with ID " + this.Trouble_id);
            }

            System.out.println("Trouble updated in the database successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating Trouble in database: " + e.getMessage());
            throw e;
        }
    }

    // Delete
    public static void deleteTrouble(int id) throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "DELETE FROM Trouble WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows were deleted for Trouble with ID " + id);
            }

            System.out.println("Trouble deleted from the database successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting Trouble from database: " + e.getMessage());
            throw e;
        }
    }
    public static List<Trouble> getTroublesByBilanId(int bilanId) throws SQLException {
        List<Trouble> troubles = new ArrayList<>();
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Trouble WHERE Bilan = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, bilanId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int troubleId = rs.getInt("Trouble_id");
                    String nom = rs.getString("nom");
                    TypeTrouble type = TypeTrouble.valueOf(rs.getString("type"));
                    int bilan = rs.getInt("Bilan");

                    Trouble trouble = new Trouble(nom, type, bilan, troubleId);
                    troubles.add(trouble);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching troubles: " + e.getMessage());
            throw e;
        }
        return troubles;
    }
}
