package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BilanOrthophonique {
    private int patient;
    private int IdOrthophoniste;
    private int Bilan_id;
    private String ProjetTherap;
    private String dateRealisation;

    public BilanOrthophonique(int patient, String dateRealisation, int IdOrthophoniste, String ProjetTherap, int Bilan_id) {
        this.patient = patient;
        this.dateRealisation = dateRealisation;
        this.IdOrthophoniste = IdOrthophoniste;
        this.ProjetTherap = ProjetTherap;

        // Insert new record into Bilan_Orthophoniste table and get Bilan_id
        if (Bilan_id > 0) {
            this.Bilan_id = Bilan_id;
        } else {
            insertBilan();
        }
    }

    private void insertBilan() {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO Bilan_Orthophonie (Patient_id, Date_Realisation, Orthophoniste_id, Projet_Therap) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, patient);
            pstmt.setString(2, dateRealisation);
            pstmt.setInt(3, IdOrthophoniste);
            pstmt.setString(4, ProjetTherap);

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.Bilan_id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating BilanOrthophonique failed, no ID obtained.");
                }
            }

            System.out.println("BilanOrthophonique added to the database successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void setBilanId(int id) {
        this.Bilan_id = id;
    }

    public int getBilanId() {
        return Bilan_id;
    }

    public int getIdOrthophoniste() {
        return IdOrthophoniste;
    }

    public static List<BilanOrthophonique> getBilansForOrthophonisteWithPatient(int orthophonisteId, int patient_Id) {
        List<BilanOrthophonique> bilans = new ArrayList<>();
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Bilan_Orthophonie WHERE Orthophoniste_id = ? AND Patient_id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, orthophonisteId);
            pstmt.setInt(2, patient_Id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int patientId = rs.getInt("Patient_id");
                String dateRealisation = rs.getString("Date_Realisation");
                int bilanId = rs.getInt(1);
                String projetTherapeutique = rs.getString("Projet_Therap");


                // Fetch patient details from Patient table using patientId
                BilanOrthophonique bilan = new BilanOrthophonique(patient_Id, dateRealisation, orthophonisteId, projetTherapeutique, bilanId);
                bilans.add(bilan);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching bilans for orthophoniste.");
            e.printStackTrace();
        }

        return bilans;
    }


}
