package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;
import esi.tp.tp_poo.Enums.TypeTerm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DossierPatient {
    private int patient;
    private int NumDossier;
    private List<RendezVous> rendezVous = new ArrayList<>();
    private List<BilanOrthophonique> bilans = new ArrayList<>();
    private List<FicheSuivi> ficheSuivi = new ArrayList<>();
    private int Ortho_id;

    public DossierPatient(int patient, int NumDossier, int OrthoId) {
        this.patient = patient;
        this.Ortho_id = OrthoId;
        if (NumDossier > 0) {
            this.NumDossier = NumDossier;
        } else {
            insertDossier();
        }
    }

    public void ajouterRendezVous(RendezVous nouveauRendezVous) {
        rendezVous.add(nouveauRendezVous);
    }

    public void ajouterBilan(BilanOrthophonique nouveauBilan) {
        bilans.add(nouveauBilan);
    }

    public void ajouterFicheSuivi(FicheSuivi ficheSuivi) {
        this.ficheSuivi.add(ficheSuivi);
    }

    public void insertDossier() {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO DossierPatient (Patient_id,Orthophoniste_id) VALUES (?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, this.patient);
            pstmt.setInt(2, this.Ortho_id);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.NumDossier = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating DossierPatient failed, no ID obtained.");
                }
            }

            System.out.println("BilanOrthophonique added to the database successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public int getNumDossier() {
        return NumDossier;
    }

}

