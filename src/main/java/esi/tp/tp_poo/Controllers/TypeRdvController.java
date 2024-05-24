package esi.tp.tp_poo.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class TypeRdvController {

    @FXML
    private Button ConsultationButton;

    @FXML
    private Button SuiviButton;

    @FXML
    private Button AtelierButton;

    @FXML
    private Button seDeconnecterButton;

    @FXML
    public void initialize() {
        ConsultationButton.setOnAction(this::handleConsultationButtonAction);
        SuiviButton.setOnAction(this::handleSuiviButtonAction);
        AtelierButton.setOnAction(this::handleAtelierButtonAction);
        seDeconnecterButton.setOnAction(this::handleSeDeconnecterButtonAction);
    }

    @FXML
    private void handleConsultationButtonAction(ActionEvent event) {
        // Your logic to handle consultation button action
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Consultation.fxml"));
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
    private void handleSuiviButtonAction(ActionEvent event) {
        // Your logic to handle suivi button action
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/RdvSuivi.fxml"));
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
    private void handleAtelierButtonAction(ActionEvent event) {
        // Your logic to handle atelier button action
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Atelier.fxml"));
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
