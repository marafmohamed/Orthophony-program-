package esi.tp.tp_poo.Controllers;

import esi.tp.tp_poo.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ListBilansController implements Initializable {
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
    private TableView<TableBilan> tableView;

    @FXML
    private TableColumn<BilanOrthophonique, String> dateColumn;

    @FXML
    private TableColumn<BilanOrthophonique, String> bilanIdColumn;

    @FXML
    private Button AjouterBilan;

    @FXML
    private Text doctorName;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CurrentBilan.getInstance().removeCurrentBilan();
        // Initialize TableView columns
        bilanIdColumn.setCellValueFactory(new PropertyValueFactory<>("BilanId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        doctorName.setText("Dr. " + Orthophoniste.getInstance().getNom() + " " + Orthophoniste.getInstance().getPrenom());


        RetourButton.setOnAction(this::handleRetourButtonAction);
        seDeconnecterButton.setOnAction(this::handleSeDeconnecterButtonAction);
        AjouterBilan.setOnAction(this::handleAjouterBilan);

        // Populate TableView with data from the database
        populateTableView();
        RdvSideBar.setOnAction(this::handleRdvSideBarAction);
        DossierSideBar.setOnAction(this::handleDossierSideBarAction);
        TestSideBar.setOnAction(this::handleTestSideBarAction);
        StatSideBar.setOnAction(this::handleStatSideBarAction);


        // Handle row click event
        tableView.setRowFactory(tv -> {
            TableRow<TableBilan> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 /*&& (!row.isEmpty())*/) {
                    TableBilan rowData = row.getItem();
                    // Your logic to handle row double click
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Bilan.fxml"));
                    Scene scene = null;
                    try {
                        scene = new Scene(loader.load());
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Couldn't load FXML file");
                    }

                    Stage stage = (Stage) tableView.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
            });
            return row;
        });
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


    private void populateTableView() {
        //   Orthophoniste.getInstance()(CurrentPatient.getInstance().getCurrentPatient().getPatient_id());
        ObservableList<TableBilan> table = FXCollections.observableArrayList();
        BilanOrthophonique.getBilansForOrthophonisteWithPatient(Orthophoniste.getInstance().getIdentifiant(), CurrentPatient.getInstance().getCurrentPatient().getPatient_id()).forEach(bilanOrthophonique -> {
            table.add(new TableBilan(bilanOrthophonique.getDateRealisation(), String.valueOf(bilanOrthophonique.getBilanId())));
        });
        System.out.println(table.size());
        tableView.setItems(table);

    }

/*@FXML
private void handleRowClicked(MouseEvent event) {
    if (event.getClickCount() == 2) { // Double-click
        // Get the selected Patient
        Patient selectedPatient = tableView.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            // You can open a new view here or perform any action you want
        }
    }
}

public void closeConnection() {
    try {
        connection.close();
    } catch (SQLException e) {
        System.err.println("Failed to close database connection: " + e.getMessage());
        // Handle exception
    }


}*/

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
    private void handleAjouterBilan(ActionEvent event) {
        Orthophoniste.getInstance().createBeilan(CurrentPatient.getInstance().getCurrentPatient().getPatient_id(),String.valueOf(LocalDate.now()) , "");
        // Your logic to handle ajouter patient button action
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Bilan.fxml"));
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

