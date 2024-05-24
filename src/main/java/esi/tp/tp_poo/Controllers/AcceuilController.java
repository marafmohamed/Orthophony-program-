package esi.tp.tp_poo.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

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



    public void initialize() {
        centeredText.layoutXProperty().bind(topPane.widthProperty().subtract(centeredText.prefWidth(-1)).divide(2));
        centeredText.layoutYProperty().bind(topPane.heightProperty().subtract(centeredText.prefHeight(-1)).divide(2));
    }

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource() == nouveauRendezVousButton) {
            System.out.println("Nouveau rendez-vous button clicked");
            // Add functionality to handle new appointment creation
        } else if (event.getSource() == dossiersPatientsButton) {
            System.out.println("Accéder aux dossiers patients button clicked");
            // Add functionality to access patient records
        } else if (event.getSource() == statistiquesButton) {
            System.out.println("Statistiques button clicked");
            // Add functionality to view statistics
        } else if (event.getSource() == parametresButton) {
            System.out.println("Paramètres button clicked");
            // Add functionality to access settings
        } else if (event.getSource() == gererTestsAnamnesesButton) {
            System.out.println("Gérer les tests et anamnèses button clicked");
            // Add functionality to manage tests and anamneses
        } else if (event.getSource() == agendaButton) {
            System.out.println("Agenda button clicked");
            // Add functionality to view agenda
        } else if (event.getSource() == seDeconnecterButton) {
            System.out.println("Se Deconnecter button clicked");
            // Add functionality to log out
        }
    }
}
