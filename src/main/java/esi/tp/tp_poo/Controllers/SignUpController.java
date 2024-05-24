package esi.tp.tp_poo.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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
    private PasswordField confirmPasswordField;

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
        String confirmPassword = confirmPasswordField.getText();

        if (validateInput(firstName, lastName, email, password, confirmPassword)) {
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

    private boolean validateInput(String firstName, String lastName, String email, String password, String confirmPassword) {
        // Add your validation logic here
        // For example, you can check if all fields are filled, if the email is in a valid format,
        // and if the password matches the confirmed password
        //return !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty() && password.equals(confirmPassword);
        return true;
    }
}