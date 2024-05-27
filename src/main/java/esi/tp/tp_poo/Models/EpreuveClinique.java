package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EpreuveClinique {
    private int epreuveId;
    private String nom;
    private List<String> observations;
    private List<Test> tests;

    public EpreuveClinique(String nom, ArrayList<String> observations, int epreuveId) throws SQLException {
        this.nom = nom;
        this.observations = observations;
        if (epreuveId > 0) {
            this.epreuveId = epreuveId;
        } else {
            insert();
        }
    }

    public int getEpreuveId() {
        return epreuveId;
    }

    public String getNom() throws SQLException {
        if (this.nom == null && this.epreuveId > 0) {
            retrieveNomFromDB();
        }
        return this.nom;
    }

    public void setNom(String nom) throws SQLException {
        this.nom = nom;
        updateAttributeInDatabase("nom", nom);
    }

    public List<String> getObservations() throws SQLException {
        if (this.observations == null && this.epreuveId > 0) {
            retrieveObservationsFromDB();
        }
        return this.observations;
    }

    public void setObservations(List<String> observations) throws SQLException {
        this.observations = observations;
        updateObservationsInDatabase();
    }

    public List<Test> getTests() {
        if (this.tests.isEmpty() && this.epreuveId > 0) {
            try {
                retrieveTestsFromDB();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return this.tests;
    }

    public void addTest(Test test) throws SQLException {
        this.tests.add(test);
    }

    private void retrieveTestsFromDB() throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();
        String sql = "SELECT * FROM Test_Epreuve WHERE Epreuve_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, this.epreuveId);
            try (ResultSet rs = pstmt.executeQuery()) {
                this.tests = new ArrayList<>();
                while (rs.next()) {
                    int testId = rs.getInt("Test_id");
                    Test test = Test.getTestById(testId);
                    if (test != null) {
                        this.tests.add(test);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert() throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO EpreuveClinique (nom) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.nom);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.epreuveId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating EpreuveClinique failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting EpreuveClinique: " + e.getMessage());
            throw e;
        }

        updateObservationsInDatabase();
    }

    private void updateAttributeInDatabase(String columnName, String value) throws SQLException {
        if (this.epreuveId <= 0) {
            throw new IllegalArgumentException("Epreuve_id is not valid. Unable to update database.");
        }

        try (Connection connection = ConnectDB.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement("UPDATE EpreuveClinique SET " + columnName + " = ? WHERE epreuveId = ?")) {

            pstmt.setString(1, value);
            pstmt.setInt(2, this.epreuveId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows were updated for epreuveId " + this.epreuveId);
            }

            System.out.println(columnName + " updated in the database successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating " + columnName + " in database: " + e.getMessage());
            throw e;
        }
    }

    private void updateObservationsInDatabase() throws SQLException {
        if (this.epreuveId <= 0) {
            throw new IllegalArgumentException("Epreuve_id is not valid. Unable to update database.");
        }

        try (Connection connection = ConnectDB.getInstance().getConnection()) {
            String deleteSql = "DELETE FROM EpreuveObservations WHERE epreuveId = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, this.epreuveId);
                deleteStmt.executeUpdate();
            }

            String insertSql = "INSERT INTO EpreuveObservations (epreuveId, observation) VALUES (?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                for (String observation : this.observations) {
                    insertStmt.setInt(1, this.epreuveId);
                    insertStmt.setString(2, observation);
                    insertStmt.executeUpdate();
                }
            }

            System.out.println("Observations updated in the database successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating observations in database: " + e.getMessage());
            throw e;
        }
    }

    private void retrieveNomFromDB() throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();
        String sql = "SELECT nom FROM EpreuveClinique WHERE epreuveId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, this.epreuveId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    this.nom = rs.getString("nom");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void retrieveObservationsFromDB() throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();
        String sql = "SELECT observation FROM EpreuveObservations WHERE epreuveId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, this.epreuveId);
            try (ResultSet rs = pstmt.executeQuery()) {
                this.observations = new ArrayList<>();
                while (rs.next()) {
                    this.observations.add(rs.getString("observation"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTest(int id) {
        Test test = Test.getTestById(id);
        if (test != null) {
            // Create a new line in the Test_Epreuve table
            try (Connection connection = ConnectDB.getInstance().getConnection();
                 PreparedStatement pstmt = connection.prepareStatement("INSERT INTO Test_Epreuve (Test_id, Epreuve_id) VALUES (?, ?)")) {
                pstmt.setInt(1, id);
                pstmt.setInt(2, this.epreuveId);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error adding Test to Epreuve: " + e.getMessage());
                e.printStackTrace();
            }
            this.tests.add(test);
        }
    }

    public void removeTest(int id) {
        Test test = Test.getTestById(id);
        if (test != null) {
            // Delete the line in the Test_Epreuve table
            try (Connection connection = ConnectDB.getInstance().getConnection();
                 PreparedStatement pstmt = connection.prepareStatement("DELETE FROM Test_Epreuve WHERE Test_id = ? AND Epreuve_id = ?")) {
                pstmt.setInt(1, id);
                pstmt.setInt(2, this.epreuveId);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error removing Test from Epreuve: " + e.getMessage());
                e.printStackTrace();
            }
            this.tests.remove(test);
        }
    }

    public static EpreuveClinique getEpreuveById(int id) {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM EpreuveClinique WHERE EpreuveClinique_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    EpreuveClinique epreuve = new EpreuveClinique(nom, new ArrayList<>(), id);
                    return epreuve;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving EpreuveClinique: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
