package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public abstract class RendezVous {
    protected int RendezVousId;
    protected int IdOrthophoniste;
    protected Date date;
    protected Time hour;

    public RendezVous(Date date, Time hour, int IdOrthophoniste) {
        this.IdOrthophoniste = IdOrthophoniste;
        this.date = date;
        this.hour = hour;
    }

    public abstract void insertRendezVous();

    public static List<RendezVous> getRendezVouswithNumDossier(int NumDossier) {
        List<RendezVous> rendezVousList = new ArrayList<>();
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT RendezVous_id FROM RendezVous_Dossier WHERE NumDossier = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, NumDossier);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<Integer> rendezVousIds = new ArrayList<>();
                while (rs.next()) {
                    int rendezVousId = rs.getInt("RendezVous_id");
                    rendezVousIds.add(rendezVousId);
                }

                for (int rendezVousId : rendezVousIds) {
                    RendezVous rendezVous = getRendezVousById(rendezVousId);
                    if (rendezVous != null) {
                        rendezVousList.add(rendezVous);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving exercises: " + e.getMessage());
            e.printStackTrace();
        }

        return rendezVousList;
    }

    private static RendezVous getRendezVousById(int rendezVousId) {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Rendez_Vous WHERE RendezVous_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, rendezVousId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("RendezVous_id");
                    String date = rs.getString("date");
                    String hour = rs.getString("Time");
                    int IdOrthophoniste = rs.getInt("Orthophoniste_id");
                    String theme = rs.getString("Thematique");
                    List <Integer> numDossier = new ArrayList<>();
                    if (theme != null) {
                        return new Atelier(Date.valueOf(date), Time.valueOf(hour), theme, IdOrthophoniste, id);
                    } else {
                        Boolean type = rs.getBoolean("Type");
                        int NumDossier = rs.getInt("NumDossier");
                        return new Suivi(Date.valueOf(date), Time.valueOf(hour), NumDossier, type, IdOrthophoniste, id);
                    }

                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching rendezvous from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setHour(Time hour) {
        this.hour = hour;
    }

    public Time getHour() {
        return hour;
    }
}
