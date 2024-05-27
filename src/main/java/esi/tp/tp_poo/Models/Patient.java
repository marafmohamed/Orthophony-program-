package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Patient {
    protected int numDossier;
    protected int Patient_id;
    protected String nom;
    protected String prenom;
    protected Date dateNaissance;
    protected String lieuNaissance;
    protected String adresse;
    protected int Orthophoniste_id;

    public Patient( String nom, String prenom, Date dateNaissance, String adresse, String lieuNaissance, int Orthophoniste_id,int NumDossier) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.adresse = adresse;
        this.Orthophoniste_id = Orthophoniste_id;
        if(NumDossier>0){
            this.numDossier=NumDossier;
        }
    }

    public int obtenirDossier() {
        // Logic to access patient's dossier
        return numDossier;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^(0\\d{9}|\\+213\\d{9})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Patient";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int patient_id=rs.getInt("Patient_id");
                int numDossier = rs.getInt("NumDossier");
                String nom = rs.getString("nom");
                String prenom = rs.getString("Prenom");
                Date dateNaissance = rs.getDate("Date_Naissance");
                String adresse = rs.getString("adresse");
                String lieuNaissance = rs.getString("Lieu_Naissance");
                String diplome = rs.getString("diplome");
                String proffession = rs.getString("proffession");
                String numTelephone = rs.getString("Phone");
                int orthophonisteId = rs.getInt("Orthophoniste_id");

                // Assuming all patients in the result set are adults for simplicity
                Adult patient = new Adult(nom, prenom, dateNaissance, adresse, lieuNaissance, diplome, proffession, numTelephone, orthophonisteId,numDossier,patient_id);
                patients.add(patient);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching patients from the database.");
            e.printStackTrace();
        }
        return patients;
    }

    public int getPatient_id() {
        return Patient_id;
    }
    public static Patient getPatientById(int id) {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Patient WHERE Patient_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int patient_id= rs.getInt("Patient_id");
                    int numDossier = rs.getInt("NumDossier");
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("Prenom");
                    String dateNaissance = rs.getString("Date_Naissance");
                    String adresse = rs.getString("adresse");
                    String lieuNaissance = rs.getString("Lieu_Naissance");
                    String etude = rs.getString("Etude");
                    String diplome = rs.getString("diplome");
                    String proffession = rs.getString("proffession");
                    String numTelephone = rs.getString("Phone");
                    int orthophonisteId = rs.getInt("Orthophoniste_id");

                    if (etude != null) {
                        // This is an enfant
                        return new Enfant(nom, prenom, Date.valueOf(dateNaissance), adresse, lieuNaissance, etude, numTelephone, orthophonisteId,numDossier,patient_id);
                    } else {
                        // This is an adult
                        return new Adult(nom, prenom, Date.valueOf(dateNaissance), adresse, lieuNaissance, diplome, proffession, numTelephone, orthophonisteId,numDossier,patient_id);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching patient from the database.");
            e.printStackTrace();
        }
        return null;
    }
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }
    public int getNumDossier(){
        System.out.println(this.numDossier);
        return this.numDossier;
    }
    public static Patient getPatientByNumDossier(int id) {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Patient WHERE NumDossier = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int patient_id= rs.getInt("Patient_id");
                    int numDossier = rs.getInt("NumDossier");
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("Prenom");
                    String dateNaissance = rs.getString("Date_Naissance");
                    String adresse = rs.getString("adresse");
                    String lieuNaissance = rs.getString("Lieu_Naissance");
                    String etude = rs.getString("Etude");
                    String diplome = rs.getString("diplome");
                    String proffession = rs.getString("proffession");
                    String numTelephone = rs.getString("Phone");
                    int orthophonisteId = rs.getInt("Orthophoniste_id");

                    if (etude != null) {
                        // This is an enfant
                        return new Enfant(nom, prenom,Date.valueOf(dateNaissance), adresse, lieuNaissance, numTelephone,etude, orthophonisteId,numDossier,patient_id);
                    } else {
                        // This is an adult
                        return new Adult(nom, prenom, Date.valueOf(dateNaissance), adresse, lieuNaissance, diplome, proffession, numTelephone, orthophonisteId,numDossier,patient_id);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching patient from the database.");
            e.printStackTrace();
        }
        return null;
    }

}