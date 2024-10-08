package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Enfant extends Patient {
    private String etude;
    private String NumTelephoneParent;

    public Enfant(String nom, String prenom, Date dateNaissance, String adresse,
                  String lieuNaissance, String NumTelephoneParent, String etude, int Orthophoniste_id, int NumDossier,int Patient_id) throws SQLException {
        super(nom, prenom, dateNaissance, adresse, lieuNaissance, Orthophoniste_id,NumDossier);

        if (!Patient.isValidPhoneNumber(NumTelephoneParent)) {
            throw new IllegalArgumentException("Invalid phone number: " + NumTelephoneParent);
        }

        this.NumTelephoneParent = NumTelephoneParent;
        this.etude = etude;
        if (Patient_id > 0) {
            this.Patient_id = Patient_id;
        } else {
            insertPatient();
        }
    }

    private void patientExists() {
        // Check if a patient with the same details exists
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Patient WHERE nom = ? AND Prenom = ? AND Date_Naissance = ? AND adresse = ? AND Lieu_Naissance = ? AND Orthophoniste_id = ? AND Phone = ? AND Etude = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, this.nom);
            pstmt.setString(2, this.prenom);
            pstmt.setString(3, String.valueOf(this.dateNaissance));
            pstmt.setString(4, this.adresse);
            pstmt.setString(5, this.lieuNaissance);
            pstmt.setInt(6, this.Orthophoniste_id);
            pstmt.setString(7, this.etude);
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

        String sql = "INSERT INTO Patient (nom, Prenom, Date_Naissance, adresse, Lieu_Naissance, Phone, Etude , Orthophoniste_id) VALUES (?, ?, ?, ?, ?, ?, ?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.nom);
            pstmt.setString(2, this.prenom);
            pstmt.setString(3,String.valueOf(this.dateNaissance));
            pstmt.setString(4, this.adresse);
            pstmt.setString(5, this.lieuNaissance);
            pstmt.setString(6, this.NumTelephoneParent);
            pstmt.setString(7, this.etude);
            pstmt.setInt(8, this.Orthophoniste_id);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.Patient_id = generatedKeys.getInt(1);
                    DossierPatient dossier= new DossierPatient(this.Patient_id,0,Orthophoniste_id);
                    this.numDossier=dossier.getNumDossier();
                    updatePatient();
                } else {
                    throw new SQLException("Creating Enfant failed, no ID obtained.");
                }
            }

            System.out.println("Enfant added to the database successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting new Enfant into the database.");
            e.printStackTrace();
        }
    }

    public String getEtude() {
        return etude;
    }

    public String getNumTelephoneParent() {
        return NumTelephoneParent;
    }
    public void updatePatient() {
        // Update patient details in the database
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "UPDATE Patient SET nom = ?, Prenom = ?, Date_Naissance = ?, adresse = ?, Lieu_Naissance = ?,etude = ?, Phone = ?, Orthophoniste_id = ? , NumDossier= ? WHERE Patient_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, this.nom);
            pstmt.setString(2, this.prenom);
            pstmt.setString(3, String.valueOf(this.dateNaissance));
            pstmt.setString(4, this.adresse);
            pstmt.setString(5, this.lieuNaissance);
            pstmt.setString(6,this.etude);
            pstmt.setString(7,this.NumTelephoneParent);
            pstmt.setInt(8, this.Orthophoniste_id);
            pstmt.setInt(9,this.numDossier);
            pstmt.setInt(10, this.Patient_id);


            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Adult patient updated successfully.");
            } else {
                System.out.println("No patient found with the provided Patient_id.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating patient: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
