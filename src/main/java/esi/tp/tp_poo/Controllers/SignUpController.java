package esi.tp.tp_poo.Controllers;

import esi.tp.tp_poo.Models.Orthophoniste;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class SignUpController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private  TextField numeroField;

    @FXML
    private  TextField adresseField;

    @FXML
    private Button signUpButton;

    @FXML
    public void initialize() {
        signUpButton.setOnAction(this::handleSignUpButtonAction);
    }

    @FXML
    private void handleSignUpButtonAction(ActionEvent event) {
        // Validate input fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (validateInput()) {
            // Your logic to handle sign-up
            try {
                // Load the home page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Acceuil.fxml"));
                Scene scene = new Scene(loader.load());

                // Get the current stage
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception properly, maybe show an alert to the user
            }
            // You can navigate to another scene or show a confirmation message here
        } else {
            // Handle invalid input, maybe show an alert to the user
        }
    }

    private boolean validateInput() {
        // Add your validation logic here
        // For example, you can check if all fields are filled, if the email is in a valid format,
        // and if the password matches the confirmed password
        //return !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty() && password.equals(confirmPassword);
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String numero = numeroField.getText();
        String adresse = adresseField.getText();

        try{
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || numero.isEmpty() || adresse.isEmpty()){
                showAlert("All fields are required.");
                return false;
            }
            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                showAlert("Invalid email format.");
                return false;
            }
            Orthophoniste.getInstance(firstName,lastName,adresse, numero ,email,password);
        }catch (Exception e){
            e.printStackTrace();
            showAlert(e.getMessage());
            return false;
        }
        return true;
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}