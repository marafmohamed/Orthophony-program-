package esi.tp.tp_poo.Controllers;

import esi.tp.tp_poo.Enums.TypePatient;
import esi.tp.tp_poo.Models.CurrentPatient;
import esi.tp.tp_poo.Models.Orthophoniste;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ConsultationController {
    @FXML
    private Button RdvSideBar;

    @FXML
    private Button DossierSideBar;

    @FXML
    private Button TestSideBar;

    @FXML
    private Button StatSideBar;
    @FXML
    private TextField patientNameField;

    @FXML
    private TextField patientPrenomField;

    @FXML
    private TextField patientAgeField;

    @FXML
    private TextField patientMotifField;

    @FXML
    private DatePicker rendezVousDatePicker;

    @FXML
    private TextArea additionalInfoTextArea;

    @FXML
    private ComboBox<String> hourComboBox;

    @FXML
    private ComboBox<String> minuteComboBox;

    @FXML
    private Button RetourButton;

    @FXML
    private Button seDeconnecterButton;

    @FXML
    private Button ValiderButton;


    @FXML
    private Button AnnulerButton;

    @FXML
    public void initialize() {
        RetourButton.setOnAction(this::handleRetourButtonAction);
        seDeconnecterButton.setOnAction(this::handleSeDeconnecterButtonAction);
        ValiderButton.setOnAction(this::handleValiderButtonAction);
        AnnulerButton.setOnAction(this::handleAnnulerButtonAction);
        RdvSideBar.setOnAction(this::handleRdvSideBarAction);
        DossierSideBar.setOnAction(this::handleDossierSideBarAction);
        TestSideBar.setOnAction(this::handleTestSideBarAction);
        StatSideBar.setOnAction(this::handleStatSideBarAction);
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

    @FXML
    private void handleValiderButtonAction(ActionEvent event) {
        // Your logic to handle enregistrer button action
        // Get the data from the fields
        String name = patientNameField.getText();
        String prenom = patientPrenomField.getText();
        String age = patientAgeField.getText();
        String motif = patientMotifField.getText();
        LocalDate rendezVousDate = rendezVousDatePicker.getValue();
        String additionalInfo = additionalInfoTextArea.getText();
        String hour = hourComboBox.getValue();
        String minute = minuteComboBox.getValue();
        if(name.isEmpty() || prenom.isEmpty() || age.isEmpty() || motif.isEmpty() || rendezVousDate == null || additionalInfo.isEmpty() || hour == null || minute == null) {
            // Show an alert to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez remplir tous les champs");
            alert.showAndWait();

        }else {
            if(Integer.parseInt(age)>=18){
                Orthophoniste.getInstance().createConsultation(Integer.parseInt(age), name, prenom, rendezVousDate, java.sql.Time.valueOf(hour + ":" + minute + ":00"), TypePatient.valueOf("ADULTE"));
            }else {
                Orthophoniste.getInstance().createConsultation(Integer.parseInt(age), name, prenom, rendezVousDate, java.sql.Time.valueOf(hour + ":" + minute + ":00"), TypePatient.valueOf("ENFANT"));
            }
            showAlert("Rendez-vous ajouté avec succès");
        }

            // Save the data to your database or data structure
        // ...
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleAnnulerButtonAction(ActionEvent event) {
        // Your logic to handle annuler button action
        // Clear all the fields
        patientNameField.clear();
        patientPrenomField.clear();
        patientAgeField.clear();
        patientMotifField.clear();
        rendezVousDatePicker.setValue(null);
        additionalInfoTextArea.clear();
        hourComboBox.setValue(null);
        minuteComboBox.setValue(null);
    }
}
