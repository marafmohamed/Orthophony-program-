package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Atelier extends RendezVous {
    private String Thematique;
    private List<Integer> DossierPatient;
    private Duration duration = Duration.ofHours(1);

    public Atelier(Date date, Time time, String Thematique, ArrayList<Integer> DossierPatient, int IdOrthophoniste, int RendezVous_id) {
        super(date, time, IdOrthophoniste);
        this.Thematique = Thematique;
        this.DossierPatient = DossierPatient;     //will change this line to search for Patients with their NumDossiers

        if (RendezVousId > 0) {
           this.RendezVousId=RendezVous_id;
        } else {
            insertRendezVous();
        }
    }
    @Override
    public void insertRendezVous() {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO RendezVous (Date,Orthophoniste_id,Time,Duration,Thematique) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, String.valueOf(this.date));
            pstmt.setInt(2, IdOrthophoniste);
            pstmt.setString(3, String.valueOf(this.hour));
            pstmt.setString(4, String.valueOf(this.duration));
            pstmt.setString(5, this.Thematique);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.RendezVousId = generatedKeys.getInt(1);
                    for (Integer DossierPatient : DossierPatient) {
                        String sql2 = "INSERT INTO RendezVous_Dossier (NumDossier,RendezVous_id) VALUES (?,?)";
                        PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                        pstmt2.setInt(1, DossierPatient);
                        pstmt2.setInt(2, IdOrthophoniste);
                        pstmt2.executeUpdate();
                    }
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
}
