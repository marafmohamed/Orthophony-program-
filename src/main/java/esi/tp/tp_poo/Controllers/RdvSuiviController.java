package esi.tp.tp_poo.Controllers;

import esi.tp.tp_poo.Models.Orthophoniste;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
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
    public void initialize() {
        // Fetch data from the database
        List<String> patients = fetchPatientsFromDatabase();

        // Create a VBox to hold the CheckBoxes
        VBox vbox = new VBox();
        vbox.setSpacing(10); // Set spacing between CheckBoxes
        vbox.setStyle("-fx-padding: 10 0 0 5;");

        // Create a ToggleGroup
        ToggleGroup group = new ToggleGroup();

        // Create a RadioButton for each patient
        for (String patient : patients) {
            RadioButton radioButton = new RadioButton(patient);
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
        String hour = hourComboBox.getValue();
        String minute = minuteComboBox.getValue();
        String additionalInfo = additionalInfoTextArea.getText();
        String deroulement = DeroulementComboBox.getValue();

        // Get the selected patient
        String selectedPatient = null;
        VBox vbox = (VBox) patientScrollPane.getContent();
        for (Node node : vbox.getChildren()) {
            if (node instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) node;
                if (radioButton.isSelected()) {
                    selectedPatient = radioButton.getText();
                    break;
                }
            }
        }

        // Save the data to your database or data structure
        // Replace this with your actual saving code
        System.out.println("Saving: " + selectedPatient + ", " + hour + ", " + minute + ", " + additionalInfo + ", " + deroulement);

    }

    private List<String> fetchPatientsFromDatabase() {
        // Replace this with your actual database fetching code
        return Arrays.asList("Patient 1, Dossier 1", "Patient 2, Dossier 2", "Patient 3, Dossier 3");
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
