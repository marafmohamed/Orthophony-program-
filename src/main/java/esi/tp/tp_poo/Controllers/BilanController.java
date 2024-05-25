package esi.tp.tp_poo.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BilanController {
    @FXML
    private Button RdvSideBar;

    @FXML
    private Button DossierSideBar;

    @FXML
    private Button TestSideBar;

    @FXML
    private Button StatSideBar;

    @FXML
    private TabPane tabPane; // Assuming you have a TabPane in your FXML

    @FXML
    private Tab epreuvesCliniquesTab; // Assuming you have a Tab with fx:id="epreuvesCliniquesTab"

    @FXML
    private Tab anamneseTab; // Assuming you have a Tab with fx:id="anamneseTab"

    @FXML
    private VBox questionContainer;

    @FXML
    private VBox testsVBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button seDeconnecterButton;

    @FXML
    private Button RetourButton;


    @FXML
    private TextField nameField; // Assuming you have a TextField with fx:id="nameField"
    @FXML
    private TextField prenomField; // Assuming you have a TextField with fx:id="prenomField"
    @FXML
    private DatePicker dateNaissancePicker; // Assuming you have a DatePicker with fx:id="dateNaissancePicker"
    @FXML
    private TextField lieuNaissanceField; // Assuming you have a TextField with fx:id="lieuNaissanceField"
    @FXML
    private TextField adresseField; // Assuming you have a TextField with fx:id="adresseField"
    @FXML
    private RadioButton enfantRadioButton; // Assuming you have a RadioButton with fx:id="enfantRadioButton"
    @FXML
    private TextField niveauField; // Assuming you have a TextField with fx:id="niveauField"
    @FXML
    private TextField numeroFieldAdlt; // Assuming you have a TextField with fx:id="numeroField"
    @FXML
    private TextField numeroFieldEnf; // Assuming you have a TextField with fx:id="numeroField"
    @FXML
    private RadioButton adultRadioButton; // Assuming you have a RadioButton with fx:id="adultRadioButton"
    @FXML
    private TextField diplomeField; // Assuming you have a TextField with fx:id="diplomeField"
    @FXML
    private TextField professionField; // Assuming you have a TextField with fx:id="professionField"
    @FXML
    private TextArea ObservationArea;




    //private List<TestController> testControllers = new ArrayList<>();

    @FXML
    public void initialize() {
        epreuvesCliniquesTab.setOnSelectionChanged(event -> {
            if (epreuvesCliniquesTab.isSelected()) {
                // Your logic when Epreuves Cliniques tab is selected
            }
        });

        anamneseTab.setOnSelectionChanged(event -> {
            if (anamneseTab.isSelected()) {
                loadInitialQuestions();
            }
        });

        enfantRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                adultRadioButton.setSelected(false);
            }
        });

        adultRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                enfantRadioButton.setSelected(false);
            }
        });

        seDeconnecterButton.setOnAction(this::handleSeDeconnecterButtonAction);
        RetourButton.setOnAction(this::handleRetourButtonAction);
        RdvSideBar.setOnAction(this::handleRdvSideBarAction);
        DossierSideBar.setOnAction(this::handleDossierSideBarAction);
        TestSideBar.setOnAction(this::handleTestSideBarAction);
        StatSideBar.setOnAction(this::handleStatSideBarAction);
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
    private void handleSaveButton() {
        // Get the data from the fields
        String name = nameField.getText();
        String prenom = prenomField.getText();
        LocalDate dateNaissance = dateNaissancePicker.getValue();
        String lieuNaissance = lieuNaissanceField.getText();
        String adresse = adresseField.getText();
        boolean isEnfant = enfantRadioButton.isSelected();
        boolean isAdult = adultRadioButton.isSelected();

        String numero = null;
        String niveau = null;
        String diplome = null;
        String profession = null;
        if (isEnfant) {
            numero = numeroFieldEnf.getText();
            niveau = niveauField.getText();
        }
        if (isAdult) {
            numero =  numeroFieldAdlt.getText();
         diplome = diplomeField.getText();
         profession = professionField.getText();
        }

        // Save the data
        String url = "jdbc:sqlite:TPdb.sqlite"; // Replace with your SQLite database path

        String sql = "INSERT INTO Anamnese(name, prenom, dateNaissance, lieuNaissance, adresse, isEnfant, niveau, numero, isAdult, diplome, profession) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, prenom);
            pstmt.setString(3, dateNaissance.toString());
            pstmt.setString(4, lieuNaissance);
            pstmt.setString(5, adresse);
            pstmt.setBoolean(6, isEnfant);
            pstmt.setString(7, niveau);
            pstmt.setString(8, numero);
            pstmt.setBoolean(9, isAdult);
            pstmt.setString(10, diplome);
            pstmt.setString(11, profession);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void loadInitialQuestions() {
        // Example initial questions
        String[] Categories = {"Structure familiale", "Dynamique familiale", "Antécédents familiaux"};
        String[] Questions = {"Quelle est la structure de votre famille?", "Comment se passe la dynamique familiale?", "Quels sont les antécédents familiaux?"};

        for (int i = 0; i < Categories.length; i++) {
            addQuestion(Categories[i], Questions[i]);
        }
    }

    private void addQuestion(String category, String question) {
        VBox questionBox = new VBox(5);

        Label categoryLabel = new Label(category + ":");
        Label questionLabel = new Label(question);
        TextField textField = new TextField();
        textField.setPromptText("Enter answer here");

        questionBox.getChildren().addAll(categoryLabel, questionLabel, textField);
        questionContainer.getChildren().add(questionBox);
    }

    @FXML
    private void handleAddQuestion(ActionEvent event) {
        // For simplicity, we'll use a static category name for new questions
        addQuestion("New Category", "New Question");
    }

    @FXML
    private void handleRemoveLastQuestion(ActionEvent event) {
        int childrenCount = questionContainer.getChildren().size();
        if (childrenCount > 0) {
            questionContainer.getChildren().remove(childrenCount - 1);
        }
    }


    @FXML
    private void handleSeDeconnecterButtonAction(ActionEvent event) {
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

}
