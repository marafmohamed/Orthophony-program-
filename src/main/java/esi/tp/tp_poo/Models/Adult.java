package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Adult extends Patient {
    private String diplome;
    private String proffession;
    private String NumTelephone;

    public Adult(String nom, String prenom, Date dateNaissance, String adresse, String lieuNaissance,
                 String diplome, String proffession, String NumTelephone, int Orthophoniste_id) throws SQLException {
        super(-1, nom, prenom, dateNaissance, adresse, lieuNaissance, Orthophoniste_id);

        if (!Patient.isValidPhoneNumber(NumTelephone)) {
            throw new IllegalArgumentException("Invalid phone number: " + NumTelephone);
        }

        this.NumTelephone = NumTelephone;
        this.diplome = diplome;
        this.proffession = proffession;

        // Check if patient already exists before inserting
        patientExists();
    }

    private void patientExists() {
        // Check if a patient with the same details exists
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Patient WHERE  nom = ? AND Prenom = ? AND Date_Naissance = ? AND adresse = ? AND Lieu_Naissance = ? AND Orthophoniste_id = ? AND diplome = ? AND Phone = ? AND proffession = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, this.nom);
            pstmt.setString(2, this.prenom);
            pstmt.setDate(3, this.dateNaissance);
            pstmt.setString(4, this.adresse);
            pstmt.setString(5, this.lieuNaissance);
            pstmt.setInt(6, this.Orthophoniste_id);
            pstmt.setString(7, this.diplome);
            pstmt.setString(8, this.NumTelephone);
            pstmt.setString(9, this.proffession);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                this.Patient_id = rs.getInt("Patient_id");
            } else {
                insertPatient();
            }
        } catch (SQLException e) {
            System.out.println("Error checking if patient exists.");
            e.printStackTrace();
        }

    }

    private void insertPatient() {
        // Insert patient into the database
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO Patient (nom, Prenom, Date_Naissance, adresse, Lieu_Naissance, diplome, proffession, Phone, Orthophoniste_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.nom);
            pstmt.setString(2, this.prenom);
            pstmt.setDate(3, this.dateNaissance);
            pstmt.setString(4, this.adresse);
            pstmt.setString(5, this.lieuNaissance);
            pstmt.setString(6, this.diplome);
            pstmt.setString(7, this.proffession);
            pstmt.setString(8, this.NumTelephone);
            pstmt.setInt(9, this.Orthophoniste_id);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.Patient_id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating Adult failed, no ID obtained.");
                }
            }

            System.out.println("Adult added to the database successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public String getDiplome() {
        return diplome;
    }

    public String getProffession() {
        return proffession;
    }

    public String getNumTelephone() {
        return NumTelephone;
    }
}
