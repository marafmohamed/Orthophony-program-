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

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class EnfantController {
    @FXML
    private Button SubmitButton;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField NiveauField;

    @FXML
    private TextField lieuNaissanceField;

    @FXML
    private DatePicker dateNaissanceField;

    @FXML
    private TextField numeroPereField;

    @FXML
    public void initialize() {
        SubmitButton.setOnAction(this::handleSubmit);
    }

    @FXML
    public void handleSubmit(ActionEvent event) {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String adresse = adresseField.getText();
        String niveau = NiveauField.getText();
        String lieuNaissance = lieuNaissanceField.getText();
        LocalDate dateNaissance = dateNaissanceField.getValue();
        String numeroPere = numeroPereField.getText();
        if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || niveau.isEmpty() || lieuNaissance.isEmpty() || dateNaissance == null ||  numeroPere.isEmpty()) {
            showAlert("Please fill in all the fields");
            return;
        }
        int age = calculateAge(dateNaissance, LocalDate.now());
        Patient patient;
        if (age>=18){
            showAlert("This form is for children only");
            return;
        }else{
            // prosses the user input
            try {
                Orthophoniste.getInstance().addPatientEnfant(adresse,numeroPere,lieuNaissance,niveau,nom,prenom,dateNaissance);
            } catch (Exception e) {
                showAlert(e.getMessage());
                throw new RuntimeException(e);
            }
            //...
            // clear the fields
            showAlert("Patient saved!");
            nomField.clear();
            prenomField.clear();
            adresseField.clear();
            NiveauField.clear();
            lieuNaissanceField.clear();
            dateNaissanceField.getEditor().clear();
            numeroPereField.clear();

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
        alert.setHeaderText((String)null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

