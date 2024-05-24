package esi.tp.tp_poo.Controllers;

import esi.tp.tp_poo.Models.Adult;
import esi.tp.tp_poo.Models.Patient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class DossiersController implements Initializable {

    @FXML
    private TableView<Patient> tableView;

    @FXML
    private TableColumn<Patient, String> nomColumn;

    @FXML
    private TableColumn<Patient, String> prenomColumn;

    @FXML
    private TableColumn<Patient, String> ageColumn;

    @FXML
    private TableColumn<Patient, String> numDossierColumn;

    private static final String SELECT_ALL_QUERY = "SELECT d.NumDossier, p.nom, p.Prenom, p.Date_Naissance FROM DossierPatient d JOIN Patient p ON d.Patient_id = p.Patient_id";
    private Connection connection;
    private Statement statement;
    public DossiersController() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:TPdb.sqlite");
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection: " + e.getMessage());
            // Handle exception
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                // Handle exceptions
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize TableView columns
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        numDossierColumn.setCellValueFactory(new PropertyValueFactory<>("numDossier"));

        // Populate TableView with data from the database
        populateTableView();
    }

    private void populateTableView() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY)) {

            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String Date_Naissance = resultSet.getString("Date_Naissance");
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy"); // replace with your date format
                java.util.Date parsed = format.parse(Date_Naissance);
                java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
                String numDossier = resultSet.getString("numDossier");

                // Pass a valid phone number
                tableView.getItems().add(new Adult(nom, prenom, sqlDate, "0", "0", "0", "0", "0690045793", 0));
            }

        } catch (SQLException | ParseException e) {
            System.err.println("Failed to populate table view: " + e.getMessage());
            // Show an error dialog to the user, or handle the error in another appropriate way
        }
    }

    @FXML
    private void handleRowClicked(MouseEvent event) {
        if (event.getClickCount() == 2) { // Double-click
            // Get the selected Patient
            Patient selectedPatient = tableView.getSelectionModel().getSelectedItem();
            if (selectedPatient != null) {
                // You can open a new view here or perform any action you want
            }
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Failed to close database connection: " + e.getMessage());
            // Handle exception
        }


    }
}