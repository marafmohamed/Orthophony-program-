package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FirstBO extends BilanOrthophonique {
    Anamnese anamnese;

    public FirstBO(int patient, String dateRealisation, int IdOrthophoniste, String ProjetTherap, int anamnese,int Bilan_id) throws SQLException {
        super(patient, dateRealisation, IdOrthophoniste, ProjetTherap,Bilan_id);
        setAnamnese(anamnese);
    }

    public void setAnamnese(int anamnese) throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();
        String sql = "SELECT * FROM Anamnese WHERE Anamnese_id=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, anamnese);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                this.anamnese = new Anamnese(anamnese,this.Bilan_id);
            } else {
                System.out.println("Anamnese does not exist in the database with this id" + anamnese);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public Anamnese getAnamnese() throws SQLException {
        if (this.anamnese == null) {
            ConnectDB db = ConnectDB.getInstance();
            Connection connection = db.getConnection();
            String sql = "SELECT * FROM Anamnese WHERE Bilan_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, this.Bilan_id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int anamneseId = rs.getInt("Anamnese_id");
                        this.anamnese = new Anamnese(anamneseId, this.Bilan_id);
                    } else {
                        System.out.println("Anamnese does not exist in the database for Bilan_id: " + this.Bilan_id);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return this.anamnese;
    }
}
