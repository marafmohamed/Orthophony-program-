package esi.tp.tp_poo.Controllers;

import esi.tp.tp_poo.ConnectDB;
import esi.tp.tp_poo.Models.CurrentPatient;
import esi.tp.tp_poo.Models.Orthophoniste;
import esi.tp.tp_poo.Models.Patient;
import esi.tp.tp_poo.Models.Suivi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class RdvSuiviController {
    @FXML
    private Button RdvSideBar;

    @FXML
    private Button DossierSideBar;

    @FXML
    private Button TestSideBar;

    @FXML
    private Button StatSideBar;
    @FXML
    private ScrollPane patientScrollPane;

    @FXML
    private ComboBox<String> hourComboBox;

    @FXML
    private ComboBox<String> minuteComboBox;

    @FXML
    private TextArea additionalInfoTextArea;

    @FXML
    private ComboBox<String> DeroulementComboBox;

    @FXML
    private Button RetourButton;
    @FXML
    private Button seDeconnecterButton;
    @FXML
    private Button validerButton;

    @FXML
    private Button annulerButton;


    @FXML
    private DatePicker Datepick;
    @FXML
    private Text doctorName;


    @FXML
    public void initialize() throws SQLException {
        // Fetch data from the database
        doctorName.setText("Dr. " + Orthophoniste.getInstance().getNom() + " " + Orthophoniste.getInstance().getPrenom());
        List<Patient> patients = Orthophoniste.getInstance().getPatientForOrthophoniste();
        // Create a VBox to hold the CheckBoxes
        VBox vbox = new VBox();
        vbox.setSpacing(10); // Set spacing between CheckBoxes
        vbox.setStyle("-fx-padding: 10 0 0 5;");

        // Create a ToggleGroup
        ToggleGroup group = new ToggleGroup();

        // Create a RadioButton for each patient
        for (Patient patient : patients) {
            String nom = patient.getNom() + patient.getPrenom() + ", " + patient.getNumDossier();
            RadioButton radioButton = new RadioButton(nom);
            radioButton.setToggleGroup(group); // Add the RadioButton to the ToggleGroup
            vbox.getChildren().add(radioButton);
        }

        // Set the VBox as the content of the ScrollPane
        patientScrollPane.setContent(vbox);

        validerButton.setOnAction(this::handleValiderButtonAction);
        annulerButton.setOnAction(this::handleAnnulerButtonAction);
        RdvSideBar.setOnAction(this::handleRdvSideBarAction);
        DossierSideBar.setOnAction(this::handleDossierSideBarAction);
        TestSideBar.setOnAction(this::handleTestSideBarAction);
        StatSideBar.setOnAction(this::handleStatSideBarAction);
        seDeconnecterButton.setOnAction(this::handleSeDeconnecterButtonAction);
        RetourButton.setOnAction(this::handleRetourButtonAction);
    }

    private void handleAnnulerButtonAction(ActionEvent actionEvent) {
        // Clear the form fields
        hourComboBox.setValue(null);
        minuteComboBox.setValue(null);
        additionalInfoTextArea.clear();
        DeroulementComboBox.setValue(null);

        // Deselect any selected radio buttons
        VBox vbox = (VBox) patientScrollPane.getContent();
        for (Node node : vbox.getChildren()) {
            if (node instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) node;
                radioButton.setSelected(false);
            }
        }
    }

    private void handleValiderButtonAction(ActionEvent actionEvent) {
        // Get the data from the form fields
        LocalDate rendezVousDate = Datepick.getValue();
        String hour = hourComboBox.getValue();
        String minute = minuteComboBox.getValue();
        String additionalInfo = additionalInfoTextArea.getText();
        String deroulement = DeroulementComboBox.getValue();

        // Get the selected patient
        Patient selectedPatient = null;
        String radioText;
        VBox vbox = (VBox) patientScrollPane.getContent();
        for (Node node : vbox.getChildren()) {
            if (node instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) node;
                if (radioButton.isSelected()) {
                    radioText = radioButton.getText();
                    // Extract the patient number from the text
                    int numDossier = Integer.parseInt(radioText.split(", ")[1]);
                    // Find the patient with the given number
                    try {
                        List<Patient> patients = Orthophoniste.getInstance().getPatientForOrthophoniste();
                        for (Patient patient : patients ) {
                            if (patient.getNumDossier() == numDossier) {
                                selectedPatient = patient;
                                break;
                            }
                        }
                        break;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Erreur lors de l'accès à la base de données");
                        alert.showAndWait();

                    }
                }
            }
        }

        // Save the data to your database or data structure
        if (hour != null && minute != null && selectedPatient != null && deroulement != null) {
            // Convert hour and minute to Time
            Time time = Time.valueOf(hour + ":" + minute + ":00");
            int numDossier = selectedPatient.getNumDossier(); // Replace with actual method
            int IdOrthophoniste = Orthophoniste.getInstance().getIdentifiant();
            int RendezVous_id = 0;

            // Convert deroulement to boolean
            boolean presentiel = deroulement.equals("Présentiel");
            // Create a new Suivi object

                Orthophoniste.getInstance().createRdvSuivi(rendezVousDate,time,numDossier,presentiel,IdOrthophoniste,RendezVous_id);
                System.out.print("Rendez-vous ajouté avec succès");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez remplir tous les champs");
            alert.showAndWait();
        }
    }


    @FXML
    private void handleRetourButtonAction(ActionEvent event) {
        // Your logic to handle retour button action
        CurrentPatient.getInstance().setCurrentPatient(null);
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

}
