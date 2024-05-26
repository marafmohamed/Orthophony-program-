package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FicheSuivi {
    private int id;
    private List<Objectif> objectifs;
    private String observations;
    private Boolean objectiveDone;
    private int NumDossier;
    public FicheSuivi(List<Objectif> objectifs, String observations, int NumDossier, int id) {
        this.objectifs = objectifs;
        this.observations = observations;
        this.objectiveDone = false;
        this.NumDossier=NumDossier;
        if(id>0){
            this.id=id;
        }else{
            insertFicheSuivi();
        }
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public List<Objectif> getObjectifs() {
        return objectifs;
    }

    public void setObjectifs(List<Objectif> objectifs) {
        this.objectifs = objectifs;
        updateFicheSuivi();
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
        updateFicheSuivi();
    }

    public Boolean getObjectiveDone() {
        return objectiveDone;
    }

    public void setObjectiveDone(Boolean objectiveDone) {
        this.objectiveDone = objectiveDone;
        updateFicheSuivi();
    }

    public void evaluerProgression(Objectif objectif, int newScore) {
        objectif.evaluer(newScore);
    }

    public double calculerProgression() {
        int totalEvolution = 0;
        int maxEvolution = objectifs.size() * 5; // Each objective can have a maximum evolution score of 5

        for (Objectif objectif : objectifs) {
            totalEvolution += objectif.getEvolution();
        }

        return ((double) totalEvolution / maxEvolution) * 100;
    }

    public void objectivesDone() {
        this.objectiveDone = true;
    }

    // Method to insert FicheSuivi into the database
    private void insertFicheSuivi() {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO FicheSuivi (observations, objectiveDone,NumDossier) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.observations);
            pstmt.setBoolean(2, this.objectiveDone);
            pstmt.setInt(3,this.NumDossier);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating FicheSuivi failed, no ID obtained.");
                }
            }

            System.out.println("FicheSuivi added to the database successfully. FicheSuivi ID: " + this.id);
        } catch (SQLException e) {
            System.out.println("Error inserting FicheSuivi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to update FicheSuivi in the database
    public void updateFicheSuivi() {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "UPDATE FicheSuivi SET observations = ?, objectiveDone = ? , NumDossier= ? WHERE FichSuivi_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, this.observations);
            pstmt.setBoolean(2, this.objectiveDone);
            pstmt.setInt(3,this.NumDossier);
            pstmt.setInt(4, this.id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows were updated for id " + this.id);
            }

            System.out.println("FicheSuivi updated in the database successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating FicheSuivi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to delete FicheSuivi from the database
    public void deleteFicheSuivi() {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "DELETE FROM FicheSuivi WHERE FichSuivi_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows were deleted for id " + this.id);
            }

            System.out.println("FicheSuivi deleted from the database successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting FicheSuivi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to get a FicheSuivi by ID
    public static FicheSuivi getFicheSuiviById(int id) throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM FicheSuivi WHERE FichSuivi_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    String observations = resultSet.getString("observations");
                    boolean objectiveDone = resultSet.getBoolean("objectiveDone");
                    int NumDossier=resultSet.getInt("NumDossier");
                    // Assuming that Objectif objects can be fetched or created in a similar way
                    List<Objectif> objectifs = Objectif.getObjectifsByFicheSuiviId(id);

                    FicheSuivi ficheSuivi = new FicheSuivi(objectifs,observations,NumDossier,id);
                    ficheSuivi.setObjectiveDone(objectiveDone);
                    ficheSuivi.id = id;

                    return ficheSuivi;
                } else {
                    throw new SQLException("FicheSuivi with ID " + id + " not found.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching FicheSuivi: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Method to get all FicheSuivis
    public static List<FicheSuivi> getAllFicheSuivis() throws SQLException {
        List<FicheSuivi> ficheSuivis = new ArrayList<>();

        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM FicheSuivi";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String observations = resultSet.getString("observations");
                boolean objectiveDone = resultSet.getBoolean("objectiveDone");
                int NumDossier=resultSet.getInt("NumDossier");
                // Assuming that Objectif objects can be fetched or created in a similar way
                List<Objectif> objectifs = Objectif.getObjectifsByFicheSuiviId(id);

                FicheSuivi ficheSuivi = new FicheSuivi(objectifs,observations,NumDossier,id);
                ficheSuivi.setObjectiveDone(objectiveDone);
                ficheSuivi.id = id;

                ficheSuivis.add(ficheSuivi);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all FicheSuivis: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        return ficheSuivis;
    }
}
