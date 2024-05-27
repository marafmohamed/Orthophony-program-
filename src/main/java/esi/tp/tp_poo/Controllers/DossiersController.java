package esi.tp.tp_poo.Controllers;

import esi.tp.tp_poo.ConnectDB;
import esi.tp.tp_poo.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class DossiersController implements Initializable {
    @FXML
    private Button RdvSideBar;

    @FXML
    private Button DossierSideBar;

    @FXML
    private Button TestSideBar;

    @FXML
    private Button StatSideBar;

    @FXML
    private Button RetourButton;
    @FXML
    private Button seDeconnecterButton;

    @FXML
    private TableView<TableDossier> tableView;

    @FXML
    private TableColumn<Patient, String> nomColumn;

    @FXML
    private TableColumn<Patient, String> prenomColumn;

    @FXML
    private TableColumn<Patient, String> ageColumn;

    @FXML
    private TableColumn<Patient, Integer> numDossierColumn;

    @FXML
    private  Button AjouterPatient;

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
//        // Initialize TableView columns
//        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
//        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
//        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
//        numDossierColumn.setCellValueFactory(new PropertyValueFactory<>("numDossier"));

        RetourButton.setOnAction(this::handleRetourButtonAction);
        seDeconnecterButton.setOnAction(this::handleSeDeconnecterButtonAction);
        AjouterPatient.setOnAction(this::handleAjouterPatient);

        // Populate TableView with data from the database
        try {
            populateTableView();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        RdvSideBar.setOnAction(this::handleRdvSideBarAction);
        DossierSideBar.setOnAction(this::handleDossierSideBarAction);
        TestSideBar.setOnAction(this::handleTestSideBarAction);
        StatSideBar.setOnAction(this::handleStatSideBarAction);

        // Initialize TableView columns
//        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
//        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
//        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
//        numDossierColumn.setCellValueFactory(new PropertyValueFactory<>("numDossier"));

        // Handle row click event
        tableView.setRowFactory(tv -> {
            TableRow<TableDossier> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 /*&& (!row.isEmpty())*/) {
                    TableDossier rowData = row.getItem();
                    // Your logic to handle row double click
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Dossier.fxml"));
                    Scene scene = null;
                    try {
                        scene = new Scene(loader.load());
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Couldn't load FXML file");
                    }

                    Stage stage = (Stage) tableView.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
            });
            return row;
        });
    }

    private void handleStatSideBarAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Statistics.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load FXML file");
        }

        Button button = (Button) actionEvent.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void handleTestSideBarAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Epreuves.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load FXML file");
        }

        Button button = (Button) actionEvent.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void handleDossierSideBarAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Dossiers.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load FXML file");
        }

        Button button = (Button) actionEvent.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void handleRdvSideBarAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TypeRdv.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load FXML file");
        }

        Button button = (Button) actionEvent.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    private void populateTableView() throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();


//        orthophoniste.addPatientAdult("Constantine", "0798765432", "Electrical Engineering", "Engineering", "Said", "Benyahia");
//        orthophoniste.addPatientAdult("Blida", "0776543210", "Civil Engineering", "Construction", "Amine", "Zerouali");
//        orthophoniste.addPatientAdult("Annaba", "0754321098", "Biology", "Science", "Yasmina", "Hamidi");

        List<Patient> patients=  Orthophoniste.getInstance().getPatientForOrthophoniste();
        ObservableList<TableDossier> table = FXCollections.observableArrayList();
        for(Patient patient : patients){
            TableDossier T;
            if(patient instanceof Enfant){
                Enfant enfant = (Enfant) patient;
                T = new TableDossier(enfant.getNom(), enfant.getPrenom(), "Enfant",String.valueOf(patient.getNumDossier()));
            }else{
                    Adult adult = (Adult) patient;
                    System.out.println(patient.getNumDossier());
                    T= new TableDossier(patient.getNom(),patient.getPrenom(),"Adulte",String.valueOf(patient.getNumDossier()));
            }
            table.add(T);
        }
        tableView.setItems(table);
    }

    @FXML
    private void handleRowClicked(MouseEvent event) {
        if (event.getClickCount() == 2) { // Double-click
            // Get the selected Patient
            TableDossier selectedPatient = tableView.getSelectionModel().getSelectedItem();
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

    @FXML
    private void handleRetourButtonAction(ActionEvent event) {
        // Your logic to handle retour button action
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Acceuil.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load FXML file");
        }

        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSeDeconnecterButtonAction(ActionEvent event) {
        Orthophoniste.disconnect();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load FXML file");
        }

        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleAjouterPatient(ActionEvent event) {
        // Your logic to handle ajouter patient button action
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Patient.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load FXML file");
        }

        Button button = (Button) event.getSource();
        Stage stage = new Stage();// (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Patient Details");
        stage.show();
    }
}