package esi.tp.tp_poo.Controllers;

import esi.tp.tp_poo.Models.CurrentPatient;
import esi.tp.tp_poo.Models.Orthophoniste;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class AtelierController {
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
    private TextField thematiqueTextField;

    @FXML
    private DatePicker rendezVousDatePicker;

    @FXML
    private Button RetourButton;

    @FXML
    private Button seDeconnecterButton;

    @FXML
    private Button validerButton;

    @FXML
    private Button annulerButton;

    @FXML
    private Text doctorName;


    @FXML
    public void initialize() {
        doctorName.setText("Dr. " + Orthophoniste.getInstance().getNom() + " " + Orthophoniste.getInstance().getPrenom());
        List<String> patients = fetchPatientsFromDatabase();

        // Create a VBox to hold the CheckBoxes
        VBox vbox = new VBox();
        vbox.setSpacing(10); // Set spacing between CheckBoxes

        // Create a CheckBox for each patient
        for (String patient : patients) {
            CheckBox checkBox = new CheckBox(patient);
            vbox.getChildren().add(checkBox);
        }

        // Set the VBox as the content of the ScrollPane
        patientScrollPane.setContent(vbox);

        RetourButton.setOnAction(this::handleRetourButtonAction);
        seDeconnecterButton.setOnAction(this::handleSeDeconnecterButtonAction);
        validerButton.setOnAction(this::handleValiderButtonAction);
        annulerButton.setOnAction(this::handleAnnulerButtonAction);
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


    private List<String> fetchPatientsFromDatabase() {
        // Replace this with your actual database fetching code
        return Arrays.asList("Patient 1, Dossier 1", "Patient 2, Dossier 2", "Patient 3, Dossier 3");
    }

    private void handleAnnulerButtonAction(ActionEvent actionEvent) {
        // Your logic to handle annuler button action
        // Clear the fields
        thematiqueTextField.clear();
        rendezVousDatePicker.setValue(null);
        additionalInfoTextArea.clear();
        hourComboBox.setValue(null);
        minuteComboBox.setValue(null);

        // Clear the selected patients
        VBox vbox = (VBox) patientScrollPane.getContent();
        for (Node node : vbox.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                checkBox.setSelected(false);
            }
        }

    }

    private void handleValiderButtonAction(ActionEvent actionEvent) {
        // Your logic to handle enregistrer button action

        // Get the data from the fields
        String thematique = thematiqueTextField.getText();
        LocalDate rendezVousDate = rendezVousDatePicker.getValue();
        String additionalInfo = additionalInfoTextArea.getText();
        String hour = hourComboBox.getValue();
        String minute = minuteComboBox.getValue();

        // Save the data to your database or data structure
        // ...
    }

    private void saveSelectedPatient(String patient) {
        // Replace this with your actual saving code
        System.out.println("Saving: " + patient);
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
        // Your logic to handle se deconnecter button action
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
}
