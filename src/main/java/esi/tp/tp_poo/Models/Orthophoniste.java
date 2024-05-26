package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Orthophoniste {
    private int identifiant;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private String motDePasse;
    private List<RendezVous> agenda = new ArrayList<>();

    public Orthophoniste(String nom, String prenom, String adresse, String telephone, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.motDePasse = motDePasse;

        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO Orthophoniste (nom, prenom, adresse, telephone, email, motDePasse) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.nom);
            pstmt.setString(2, this.prenom);
            pstmt.setString(3, this.adresse);
            pstmt.setString(4, this.telephone);
            pstmt.setString(5, this.email);
            pstmt.setString(6, this.motDePasse);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.identifiant = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating Orthophoniste failed, no ID obtained.");
                }
            }

            System.out.println("Orthophoniste added to the database successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting new Orthophoniste into the database.");
            e.printStackTrace();
        }
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void planifierRendezVous(RendezVous rendezVous) {
        agenda.add(rendezVous);
    }

    public int connect(String email, String password) {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();
        String sql = "SELECT identifiant FROM Orthophoniste WHERE email = ? AND motDePasse = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Orthophoniste with the provided email and password exists
                return rs.getInt("identifiant");
            } else {
                // Orthophoniste does not exist or incorrect credentials
                return -1;
            }
        } catch (SQLException e) {
            System.out.println("Error connecting orthophoniste.");
            e.printStackTrace();
            return -1;
        }
    }

    public Adult addPatientAdult(String adresse, String telephone, String diplome, String proffession, String nom, String Prenom, LocalDate DateNaissance, String lieuNaissance) throws SQLException {
        return new Adult(nom, Prenom, Date.valueOf(DateNaissance) , adresse, lieuNaissance, diplome, proffession, telephone, this.identifiant,0,0);
    }
    public Enfant addPatientEnfant(String adresse, String telephone, String lieuNaissance, String etude, String nom, String Prenom,LocalDate DateNaissance) throws SQLException {
        return new Enfant(nom,Prenom,Date.valueOf(DateNaissance),adresse,lieuNaissance,telephone,etude,this.identifiant,0,0);
    }
    public BilanOrthophonique CreateBilan(int Patient_id) {
        BilanOrthophonique bilan = new BilanOrthophonique(Patient_id, "10/12/2024", this.identifiant, "kkk",0);
        return bilan;
    }

    public List<Patient> getPatientForOrthophoniste() throws SQLException {
        List<Patient> patients = new ArrayList<>();

        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Patient WHERE Orthophoniste_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, this.identifiant);
            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int patient_id= rs.getInt(1);
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
                        patients.add(new Enfant(nom, prenom, Date.valueOf(dateNaissance), adresse, lieuNaissance, numTelephone,etude, orthophonisteId,numDossier,patient_id));
                    } else {
                        // This is an adult
                        System.out.println("i am here "+patient_id);


                     patients.add(new Adult(nom, prenom, Date.valueOf(dateNaissance), adresse, lieuNaissance, diplome, proffession, numTelephone, orthophonisteId,numDossier,patient_id));
                        System.out.println("kkkk "+ patients.size());
                    }

                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Patient: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return patients;
    }
    public void modifierInformationPersonnelle(String information) {
        // Implementation needed - modify fields based on the information argument
    }
}
