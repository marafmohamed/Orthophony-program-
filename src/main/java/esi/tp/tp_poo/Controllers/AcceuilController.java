package esi.tp.tp_poo.Controllers;

import esi.tp.tp_poo.HelloApplication;
import esi.tp.tp_poo.Models.Orthophoniste;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AcceuilController {
    @FXML
    private Pane topPane;

    @FXML
    private Text centeredText;

    @FXML
    private Button nouveauRendezVousButton;

    @FXML
    private Button dossiersPatientsButton;

    @FXML
    private Button statistiquesButton;

    @FXML
    private Button parametresButton;

    @FXML
    private Button gererTestsAnamnesesButton;

    @FXML
    private Button agendaButton;

    @FXML
    private Button seDeconnecterButton;

    @FXML
    private Text doctorName;

    @FXML
    public void initialize() {
        // Set the event handlers for the buttons
        doctorName.setText("Dr. " + Orthophoniste.getInstance().getNom() + " " + Orthophoniste.getInstance().getPrenom());
        nouveauRendezVousButton.setOnAction(this::handleNouveauRendezVousButtonAction);
        dossiersPatientsButton.setOnAction(this::handleDossiersPatientsButtonAction);
        statistiquesButton.setOnAction(this::handleStatistiquesButtonAction);
        parametresButton.setOnAction(this::handleParametresButtonAction);
        gererTestsAnamnesesButton.setOnAction(this::handleGererTestsAnamnesesButtonAction);
        agendaButton.setOnAction(this::handleAgendaButtonAction);
        seDeconnecterButton.setOnAction(this::handleSeDeconnecterButtonAction);
    }

    @FXML
    private void handleNouveauRendezVousButtonAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TypeRdv.fxml"));
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
    // Add other button handlers here

    @FXML
    private void handleDossiersPatientsButtonAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Dossiers.fxml"));
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
        stage.show();    }

    @FXML
    private void handleStatistiquesButtonAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Statistics.fxml"));
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
    private void handleParametresButtonAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Parametres.fxml"));
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
    private void handleGererTestsAnamnesesButtonAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Epreuves.fxml"));
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
    private void handleAgendaButtonAction(ActionEvent event) {

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
}