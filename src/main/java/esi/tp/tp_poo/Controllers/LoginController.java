package esi.tp.tp_poo.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Text signUpText;

    // Initialize method to set up event handlers
    @FXML
    public void initialize() {
        loginButton.setOnAction(this::handleLoginButtonAction);
        signUpText.setOnMouseClicked(event -> handleSignUpTextAction());
    }

    @FXML
    // Handle login button action
    private void handleLoginButtonAction(ActionEvent event) {
        // Your logic to handle login
        if (isLoginValid()) {
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
        } else {
            // Handle invalid login, maybe show an alert to the user
        }
    }

    // Handle sign-up text action
    private void handleSignUpTextAction() {
        // Your logic to handle sign up
        try {
            // Load the sign-up page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/SignUp.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage
            Stage stage = (Stage) signUpText.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception properly, maybe show an alert to the user
        }
    }

    private boolean isLoginValid() {
        // Add your login validation logic here
        /*String email = emailField.getText();
        String password = passwordField.getText();
        return email.equals("yourEmail@example.com") && password.equals("yourPassword");*/
        return true;
    }
}
