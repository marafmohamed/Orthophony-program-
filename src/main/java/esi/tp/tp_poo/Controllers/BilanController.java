package esi.tp.tp_poo.Controllers;

import esi.tp.tp_poo.Models.Orthophoniste;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.*;
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
    private TabPane tabPane;

    @FXML
    private Tab epreuvesCliniquesTab;

    @FXML
    private Tab anamneseTab;

    @FXML
    private VBox questionContainer;

    @FXML
    private Button seDeconnecterButton;

    @FXML
    private Button RetourButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField nameField;
    @FXML
    private TextField prenomField;
    @FXML
    private DatePicker dateNaissancePicker;
    @FXML
    private TextField lieuNaissanceField;
    @FXML
    private TextField adresseField;
    @FXML
    private RadioButton enfantRadioButton;
    @FXML
    private TextField niveauField;
    @FXML
    private TextField numeroFieldAdlt;
    @FXML
    private TextField numeroFieldEnf;
    @FXML
    private RadioButton adultRadioButton;
    @FXML
    private TextField diplomeField;
    @FXML
    private TextField professionField;
    @FXML
    private TextArea ObservationArea;
    @FXML
    private Accordion testsAccordion;


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
        saveButton.setOnAction(this::handleSaveButton);

        loadTestsFromDatabase();
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
    private void handleSaveButton(ActionEvent actionEvent) {
        String url = "jdbc:sqlite:TPdb.sqlite"; // Replace with your SQLite database path
        try (Connection conn = DriverManager.getConnection(url)) {
            if (anamneseTab.isSelected()) {
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
                    numero = numeroFieldAdlt.getText();
                    diplome = diplomeField.getText();
                    profession = professionField.getText();
                }

                // Save the data
                String sql = "INSERT INTO Anamnese(name, prenom, dateNaissance, lieuNaissance, adresse, isEnfant, niveau, numero, isAdult, diplome, profession) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
                }
            } else if (epreuvesCliniquesTab.isSelected()) {
                // Get the data from the fields
                String observations = ObservationArea.getText();

                // Iterate over each TitledPane in the Accordion
                for (TitledPane pane : testsAccordion.getPanes()) {
                    VBox content = (VBox) pane.getContent();
                    // The first HBox contains the test name and type
                    HBox testNameAndTypeBox = (HBox) content.getChildren().get(0);
                    String testName = ((Label) testNameAndTypeBox.getChildren().get(0)).getText();
                    String testType = ((Label) testNameAndTypeBox.getChildren().get(1)).getText();

                    // The second VBox contains the questions
                    VBox questionsBox = (VBox) content.getChildren().get(1);
                    ArrayList<String> selectedChoices = new ArrayList<>();
                    for (Node node : questionsBox.getChildren()) {
                        if (node instanceof CheckBox && ((CheckBox) node).isSelected()) {
                            selectedChoices.add(((CheckBox) node).getText());
                        }
                    }

                    // Save the data
                    // Replace this with your actual saving code
                    System.out.println("Saving: " + testName + ", " + testType + ", " + selectedChoices);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/CompteRendu.fxml"));
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

    private void loadTestsFromDatabase() {
        String url = "jdbc:sqlite:TPdb.sqlite"; // Replace with your SQLite database path
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Test"; // Replace with your actual SQL query to fetch the tests
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String testName = rs.getString("nom"); // Replace with your actual column name for the test name
                    String testCapacity = rs.getString("capacite"); // Replace with your actual column name for the test capacity

                    TitledPane pane = new TitledPane();
                    pane.setText(testName + " + " + testCapacity);

                    VBox content = new VBox(10);
                    // Replace with your actual code to fetch the questions for each test and create CheckBoxes for them
                    // ...

                    pane.setContent(content);
                    testsAccordion.getPanes().add(pane);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private void loadInitialQuestions() {
        /*// Example initial questions
        String[] Categories = {"Structure familiale", "Dynamique familiale", "Antécédents familiaux"};
        String[] Questions = {"Quelle est la structure de votre famille?", "Comment se passe la dynamique familiale?", "Quels sont les antécédents familiaux?"};

        for (int i = 0; i < Categories.length; i++) {
            addQuestion(Categories[i], Questions[i]);
        }*/
        String url = "jdbc:sqlite:TPdb.sqlite"; // Replace with your SQLite database path
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Question_Anamnese"; // Replace with your actual SQL query to fetch the anamnese questions
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String enonce = rs.getString("Enonce"); // Replace with your actual column name for the question text
                    String category = rs.getString("Category"); // Replace with your actual column name for the category

                    addQuestion(category, enonce);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
