package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;
import esi.tp.tp_poo.Enums.TypePatient;

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

    private static Orthophoniste instance = null;

    // Private constructor to prevent direct instantiation
    private Orthophoniste(int identifiant, String nom, String prenom, String adresse, String telephone, String email, String motDePasse) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    // Static method to get the single instance of the class
    public static Orthophoniste getInstance(String nom, String prenom, String adresse, String telephone, String email, String motDePasse) {
        if (instance == null) {
            if (!emailExists(email)) {
                createNewOrthophoniste(nom, prenom, adresse, telephone, email, motDePasse);
            } else {
                throw new IllegalArgumentException("An Orthophoniste with this email already exists.");
            }
        }
        return instance;
    }

    // Static method to get the instance if already created
    public static Orthophoniste getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Orthophoniste instance has not been created yet. Please call getInstance with parameters first.");
        }
        return instance;
    }

    // Method to disconnect and delete the instance
    public static void disconnect() {
        instance = null;
        System.out.println("Orthophoniste instance has been disconnected.");
    }

    // Method to check if the email already exists in the database
    private static boolean emailExists(String email) {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();
        String sql = "SELECT COUNT(*) FROM Orthophoniste WHERE email = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking email existence.");
            e.printStackTrace();
        }
        return false;
    }

    // Method to create a new Orthophoniste and store in the database
    private static void createNewOrthophoniste(String nom, String prenom, String adresse, String telephone, String email, String motDePasse) {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO Orthophoniste (nom, prenom, adresse, telephone, email, motDePasse) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, adresse);
            pstmt.setString(4, telephone);
            pstmt.setString(5, email);
            pstmt.setString(6, motDePasse);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int identifiant = generatedKeys.getInt(1);
                    instance = new Orthophoniste(identifiant, nom, prenom, adresse, telephone, email, motDePasse);
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

    // Modified connect method to create an instance if email and password match
    public static Orthophoniste connect(String email, String password) {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();
        String sql = "SELECT * FROM Orthophoniste WHERE email = ? AND motDePasse = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int identifiant = rs.getInt("identifiant");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String adresse = rs.getString("adresse");
                String telephone = rs.getString("telephone");
                String motDePasse = rs.getString("motDePasse");

                instance = new Orthophoniste(identifiant, nom, prenom, adresse, telephone, email, motDePasse);
                return instance;
            } else {
                // Orthophoniste does not exist or incorrect credentials
                throw new IllegalArgumentException("Invalid email or password.");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting orthophoniste.");
            e.printStackTrace();
            return null;
        }
    }

    public static String getNom() {
        return instance.nom;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void planifierRendezVous(RendezVous rendezVous) {
        agenda.add(rendezVous);
    }

    public Adult addPatientAdult(String adresse, String telephone, String diplome, String proffession, String nom, String Prenom, LocalDate DateNaissance, String lieuNaissance) throws SQLException {
        return new Adult(nom, Prenom, Date.valueOf(DateNaissance), adresse, lieuNaissance, diplome, proffession, telephone, this.identifiant, 0, 0);
    }

    public Enfant addPatientEnfant(String adresse, String telephone, String lieuNaissance, String etude, String nom, String Prenom, LocalDate DateNaissance) throws SQLException {
        return new Enfant(nom, Prenom, Date.valueOf(DateNaissance), adresse, lieuNaissance, telephone, etude, this.identifiant, 0, 0);
    }

    public void createConsultation(int age, String nom, String prenom, LocalDate date, Time hour, TypePatient type) {
        new Consultation(age, nom, prenom, Date.valueOf(date), hour, this.identifiant, type,0);
    }

    public void createRdvSuivi(LocalDate date, Time hour, int numDossier, boolean presentiel, int IdOrthophoniste, int RendezVous_id) {
        new Suivi(Date.valueOf(date), hour, numDossier, presentiel, IdOrthophoniste, RendezVous_id);
    }

    public void createAtelier(LocalDate date, Time time, String Thematique, int IdOrthophoniste, List<Integer>DossierPatients,int RendezVous_id) {
        new Atelier(Date.valueOf(date), time, Thematique, IdOrthophoniste,DossierPatients, RendezVous_id);
    }
    public BilanOrthophonique createBeilan(int Patient_id, String dateRealisation, String projetTherapeutique) {
        return new BilanOrthophonique(Patient_id, dateRealisation, this.identifiant, projetTherapeutique, 0);
    }
    public List<BilanOrthophonique> getAllBilanForPatient (int Patient_id){
        return null;
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
                    int patient_id = rs.getInt(1);
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
                        patients.add(new Enfant(nom, prenom, Date.valueOf(dateNaissance), adresse, lieuNaissance, numTelephone, etude, orthophonisteId, numDossier, patient_id));
                    } else {
                        // This is an adult
                        System.out.println("i am here " + patient_id);
                        patients.add(new Adult(nom, prenom, Date.valueOf(dateNaissance), adresse, lieuNaissance, diplome, proffession, numTelephone, orthophonisteId, numDossier, patient_id));
                        System.out.println("kkkk " + patients.size());
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

    public String getPrenom() {
        return prenom;
    }
}
