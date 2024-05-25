package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExerciceMateriel extends Exercice {

    private String Materiel;

    public ExerciceMateriel(String enonce, String reponse, String Materiel, String consigne, int Exo_id) throws SQLException {
        super(enonce, reponse, consigne, Exo_id);
        if(Exo_id>0){
            this.Materiel = Materiel;
        }else{
            try {
                setMateriel(Materiel);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getMateriel() throws SQLException {
        if (this.Materiel != null) {
            return this.Materiel;
        } else if (this.Exo_id > 0) {
            // Search for Materiel by Exo_id in the database
            this.Materiel = fetchDataFromDatabase("Material", "Exercice", this.Exo_id);
        }
        return null;
    }

    public void setMateriel(String Materiel) throws SQLException {
        this.Materiel = Materiel;
        updateAttributeInDatabase("Materiel", Materiel);
    }


    private String fetchDataFromDatabase(String columnName, String tableName, int id) throws SQLException {
        try (Connection connection = ConnectDB.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT " + columnName + " FROM " + tableName + " WHERE Exo_id = ?")) {

            pstmt.setInt(1, id);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(columnName);
                } else {
                    throw new SQLException(tableName + " with ID " + id + " not found.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching data from database: " + e.getMessage());
            throw e;
        }
    }
}
