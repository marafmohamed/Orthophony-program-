package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.*;
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

    public Adult addPatientAdult(String adresse, String telephone, String diplome, String proffession, String nom, String Prenom) throws SQLException {
        return new Adult(nom, Prenom, Date.valueOf("2024-05-23"), adresse, "lll", diplome, proffession, telephone, this.identifiant);
    }

    public BilanOrthophonique CreateBilan(int Patient_id) {
        BilanOrthophonique bilan = new BilanOrthophonique(Patient_id, "10/12/2024", this.identifiant, "kkk", 0);
        return bilan;
    }

    public void modifierInformationPersonnelle(String information) {
        // Implementation needed - modify fields based on the information argument
    }
}
