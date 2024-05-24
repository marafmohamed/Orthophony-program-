package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;
import esi.tp.tp_poo.Enums.TypePatient;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.time.Duration;

public class Consultation extends RendezVous {
    private String nom;
    private String prenom;
    private int age;
    private int NumDossier;
    private TypePatient typePatient;
    private Duration duration;

    // num de dossier
    public Consultation(int age, String nom, String prenom, Date date, Time hour, int IdOrthophoniste, @NotNull TypePatient typePatient) {
        super(date, hour, IdOrthophoniste);
        this.age = age;
        this.nom = nom;
        this.prenom = prenom;
        switch (typePatient) {
            case ADULTE -> {
                this.duration = Duration.ofMinutes(90);
                break;
            }
            case ENFANT -> {
                this.duration = Duration.ofMinutes(150);
                break;
            }
        }
        this.typePatient = typePatient;
    }

    public void setNumDossier(int NumDossier) {
        this.NumDossier = NumDossier;
    }


    @Override
    public void insertRendezVous() {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO RendezVous (Date,Orthophoniste_id,Time,Duration,Nom,Age,Prenom) VALUES (?, ?, ?, ?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, String.valueOf(this.date));
            pstmt.setInt(2, IdOrthophoniste);
            pstmt.setString(3, String.valueOf(this.hour));
            pstmt.setString(4, String.valueOf(this.duration));
            pstmt.setString(5, this.nom);
            pstmt.setInt(6,age);
            pstmt.setString(7, this.prenom);


            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.RendezVousId = generatedKeys.getInt(1);
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
