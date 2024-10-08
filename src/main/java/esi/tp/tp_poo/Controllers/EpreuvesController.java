package esi.tp.tp_poo.Controllers;

import esi.tp.tp_poo.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EpreuvesController {
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
    private Button CreerEpreuveButton;

    @FXML
    private Button ModifierEpreuveButton;

    @FXML
    private Button SupprimerEpreuveButton;

    @FXML
    private Text doctorName;
    @FXML
    private TableView<TestTable> tableView1;
    @FXML
    private TableColumn<BilanOrthophonique, String> NomColumn;

    @FXML
    private TableColumn<BilanOrthophonique, String> CapaciteColumn;

    @FXML
    public void initialize() {
        doctorName.setText("Dr. " + Orthophoniste.getInstance().getNom() + " " + Orthophoniste.getInstance().getPrenom());
        RetourButton.setOnAction(this::handleRetourButtonAction);
        seDeconnecterButton.setOnAction(this::handleSeDeconnecterButtonAction);
        RdvSideBar.setOnAction(this::handleRdvSideBarAction);
        DossierSideBar.setOnAction(this::handleDossierSideBarAction);
        TestSideBar.setOnAction(this::handleTestSideBarAction);
        StatSideBar.setOnAction(this::handleStatSideBarAction);
        CreerEpreuveButton.setOnAction(this::handleCreerEpreuveButtonAction);
        NomColumn.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        CapaciteColumn.setCellValueFactory(new PropertyValueFactory<>("Capacite"));
        populateView();
        ModifierEpreuveButton.setOnAction(this::handleModifierEpreuveButtonAction);
    }
    private void populateView(){
        // Your logic to populate the view
        List<Test> tests=Test.getAllTests();
        System.out.println(tests.size());
        ObservableList<TestTable> table = FXCollections.observableArrayList();
        for (Test test:tests){
            table.add(new TestTable(test.getNomTest(),test.getCapacite()));
        }
        tableView1.setItems(table);
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
    private void handleCreerEpreuveButtonAction(ActionEvent event) {
        // Your logic to handle creer epreuve button action
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Test.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load FXML file");
        }

        Button button = (Button) event.getSource();
        Stage stage = new Stage();// (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Ajouter une épreuve");
        stage.show();
    }

    @FXML
    private void handleModifierEpreuveButtonAction(ActionEvent event) {
        // Your logic to handle creer epreuve button action
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Test.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load FXML file");
        }

        Button button = (Button) event.getSource();
        Stage stage = new Stage();// (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Ajouter une épreuve");
        stage.show();
    }

    @FXML
    private void handleSupprimerEpreuveButtonAction(ActionEvent event) {
        // Your logic to handle supprimer epreuve button action
        // ...
    }
}