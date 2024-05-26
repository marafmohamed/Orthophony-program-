package esi.tp.tp_poo.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import esi.tp.tp_poo.Models.Orthophoniste;
import esi.tp.tp_poo.Models.Adult;
import esi.tp.tp_poo.Models.Patient;
import esi.tp.tp_poo.Models.Enfant;


import java.time.LocalDate;
import java.time.Period;

public class AdultController {

    @FXML
    private Button SubmitButton;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField diplomeField;

    @FXML
    private TextField lieuNaissanceField;

    @FXML
    private DatePicker dateNaissanceField;

    @FXML
    private TextField numeroField;

    @FXML
    private TextField professionField;

    @FXML
    public void initialize() {
        SubmitButton.setOnAction(this::handleSubmit);
    }

    @FXML
    private void handleSubmit(ActionEvent actionEvent) {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String adresse = adresseField.getText();
        String diplome = diplomeField.getText();
        String lieuNaissance = lieuNaissanceField.getText();
        LocalDate dateNaissance = dateNaissanceField.getValue();
        String numero = numeroField.getText();
        String profession = professionField.getText();
        if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || diplome.isEmpty() || lieuNaissance.isEmpty() || dateNaissance == null || numero.isEmpty() || profession.isEmpty()) {
            showAlert("Please fill in all the fields");
            return;
        }
        int age = calculateAge(dateNaissance, LocalDate.now());
        // Process the user input
        Patient patient;
        if (age < 18) {
            showAlert("The patient must be a minor. Please fill in the child form.");
        } else {
            // create a new adult patient
            // ...
            showAlert("Patient saved!");
            // clear the form
            nomField.clear();
            prenomField.clear();
            adresseField.clear();
            diplomeField.clear();
            lieuNaissanceField.clear();
            dateNaissanceField.getEditor().clear();
            numeroField.clear();
            professionField.clear();
        }
    }

    private int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if (birthDate == null || currentDate == null) {
            return 0;
        }
        return Period.between(birthDate, currentDate).getYears();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText((String) null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

