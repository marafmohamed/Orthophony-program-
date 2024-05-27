package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Test {
    protected int Test_id;
    protected String nomTest;
    protected String Capacité;

    public Test(String nomTest, String capacité) {
        this.nomTest = nomTest;
        this.Capacité = capacité;
    }
    public abstract void insertTest();

    public static Test getTestById(int id) {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Test WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    String capacite = rs.getString("Capacite");
                    boolean exo = rs.getBoolean("exo");

                    if (exo) {
                        return new TestExercices(nom, capacite, id);
                    } else {
                        return new TestQuestions(nom, capacite, id);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving Test: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}