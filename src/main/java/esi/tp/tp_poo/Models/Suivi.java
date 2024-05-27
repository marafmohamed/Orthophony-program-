package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.*;
import java.time.Duration;

public class Suivi extends RendezVous {

    private int numDossier;
    private boolean presentiel;
    private Duration duration = Duration.ofHours(1);

    public Suivi(Date date, Time hour, int numDossier, boolean presentiel, int IdOrthophoniste, int RendezVous_id) {
        super(date, hour, IdOrthophoniste);
        this.numDossier = numDossier;
        this.presentiel = presentiel;
        if (RendezVous_id > 0) {
            this.RendezVousId = RendezVous_id;
        } else {
            insertRendezVous();
        }
    }

    @Override
    public void insertRendezVous() {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO Rendez_Vous (Date,Orthophoniste_id,Time,Duration,Type , NumDossier) VALUES (?, ?, ?, ?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, String.valueOf(this.date));
            pstmt.setInt(2, IdOrthophoniste);
            pstmt.setString(3, String.valueOf(this.hour));
            pstmt.setString(4, String.valueOf(this.duration));
            pstmt.setBoolean(5, this.presentiel);
            pstmt.setInt(6, this.numDossier);

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.RendezVousId = generatedKeys.getInt(1);
                    String sql2 = "INSERT INTO RendezVous_Dossier (NumDossier,RendezVous_id) VALUES (?,?)";
                    PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                    pstmt2.setInt(1, numDossier);
                    pstmt2.setInt(2, RendezVousId);
                    pstmt2.executeUpdate();
                } else {
                    throw new SQLException("Creating RendezVous failed, no ID obtained.");
                }
            }

            System.out.println("RendezVous added to the database successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public int getNumDossier() throws SQLException {
        if (numDossier != -1) {
            return numDossier;
        }

        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();
        String sql = "SELECT NumDossier FROM RendezVous WHERE RendezVous_id=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, RendezVousId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            this.numDossier = rs.getInt("NumDossier");
            return numDossier;
        }
        return -1;
    }
}

