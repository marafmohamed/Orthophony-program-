package esi.tp.tp_poo.Controllers;

import esi.tp.tp_poo.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import esi.tp.tp_poo.Models.Orthophoniste;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DossierController {
    @FXML
    private Button RdvSideBar;

    @FXML
    private Button DossierSideBar;

    @FXML
    private Button TestSideBar;

    @FXML
    private Button StatSideBar;

    @FXML
    private Button RetourButton;

    @FXML
    private Button seDeconnecterButton;

    @FXML
    private Button AccederBilansButton;

    @FXML
    private Button AccederFichesButton;

    @FXML
    private TableView<LineRendezVous> tableView;

    @FXML
    private TableColumn<LineRendezVous, String> dateColumn;

    @FXML
    private TableColumn<RendezVous,String> hourColumn;

    @FXML
    private TableColumn<RendezVous, String> typeColumn;

    @FXML
    private TableColumn<Patient, Integer> numDossierColumn;
    public void initialize() throws SQLException {
        RetourButton.setOnAction(this::handleRetourButtonAction);
        seDeconnecterButton.setOnAction(this::handleSeDeconnecterButtonAction);
        RdvSideBar.setOnAction(this::handleRdvSideBarAction);
        DossierSideBar.setOnAction(this::handleDossierSideBarAction);
        TestSideBar.setOnAction(this::handleTestSideBarAction);
        StatSideBar.setOnAction(this::handleStatSideBarAction);
        AccederBilansButton.setOnAction(this::handleAccederBilansButtonAction);
        AccederFichesButton.setOnAction(this::handleAccederFichesButtonAction);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        hourColumn.setCellValueFactory(new PropertyValueFactory<>("Heure"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeRendezVous"));
        populateTableView();
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
    private void handleAccederBilansButtonAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ListBilans.fxml"));
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
    private void handleAccederFichesButtonAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ListSuivi.fxml"));
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

    private void populateTableView() {
        List<RendezVous> r = RendezVous.getRendezVouswithNumDossier(CurrentPatient.getInstance().getCurrentPatient().getNumDossier());
        ObservableList<LineRendezVous> table = FXCollections.observableArrayList();
        for (RendezVous rendezVous : r) {
            if (rendezVous instanceof Atelier atelier) {
                table.add(new LineRendezVous(String.valueOf(atelier.getDate()), String.valueOf(atelier.getHour()), "Atelier"));
            } else {
                System.out.println(rendezVous.getDate());
                table.add(new LineRendezVous(String.valueOf(rendezVous.getDate()), String.valueOf(rendezVous.getHour()), "Suivi"));
            }
        }

        tableView.setItems(table);
    }
}
