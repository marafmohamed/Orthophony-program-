package esi.tp.tp_poo.Controllers;

import esi.tp.tp_poo.Models.Orthophoniste;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
        anamneseTab.setOnSelectionChanged(event -> {
            if (anamneseTab.isSelected()) {
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
                loadInitialQuestions();
            }
        });

        epreuvesCliniquesTab.setOnSelectionChanged(event -> {
            if (epreuvesCliniquesTab.isSelected()) {
                loadTestsFromDatabase();
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

                    if (testType.equals("QCM")) {
                        ArrayList<String> selectedChoices = new ArrayList<>();
                        for (Node node : questionsBox.getChildren()) {
                            if (node instanceof CheckBox && ((CheckBox) node).isSelected()) {
                                selectedChoices.add(((CheckBox) node).getText());
                            }
                        }
                        // Save the data
                        // Replace this with your actual saving code
                        System.out.println("Saving QCM: " + testName + ", " + selectedChoices);
                    } else if (testType.equals("QCU")) {
                        String selectedChoice = null;
                        for (Node node : questionsBox.getChildren()) {
                            if (node instanceof RadioButton && ((RadioButton) node).isSelected()) {
                                selectedChoice = ((RadioButton) node).getText();
                                break;
                            }
                        }
                        // Save the data
                        // Replace this with your actual saving code
                        System.out.println("Saving QCU: " + testName + ", " + selectedChoice);
                    } else if (testType.equals("Exercises")) {
                        // Handle exercises type
                        // This will depend on how you have structured your exercises
                        // Replace this with your actual saving code
                        System.out.println("Saving Exercises: " + testName);
                    }
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
        /*String url = "jdbc:sqlite:TPdb.sqlite"; // Replace with your SQLite database path
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Test"; // Replace with your actual SQL query to fetch the tests
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String testName = rs.getString("nom"); // Replace with your actual column name for the test name
                    String testCapacity = rs.getString("capacite"); // Replace with your actual column name for the test capacity
                    //String testType = rs.getString("type"); // Replace with your actual column name for the test type
*/
                    TitledPane pane = new TitledPane();
                    //pane.setText(testName + " + " + testCapacity);
                    pane.setText("name , capacité");

                    if (true) {

                        HBox testNameAndTypeBox = new HBox(10);
                        testNameAndTypeBox.setAlignment(Pos.CENTER_LEFT);
                        Label testNameLabel = new Label("Test Questionnaire");
                        Label testTypeLabel = new Label("Type QCM");
                        testNameAndTypeBox.getChildren().addAll(testNameLabel, testTypeLabel);
                        //addQuestionQcm("Question 1", new String[]{"choix 1", "choix 2", "choix 3"});
                        VBox questionsBox = new VBox(5);
                        Label question1 = new Label("question");
                        questionsBox.getChildren().add(question1);
                        String[] choices = {"choix 1", "choix 6", "choix 3"};
                        for(String choice : choices) {
                            CheckBox choice1 = new CheckBox(choice);
                            questionsBox.getChildren().add(choice1);
                        }
                        VBox Qstbox = new VBox(5);
                        Qstbox.getChildren().addAll(testNameAndTypeBox, questionsBox);
                        pane.setContent(Qstbox);



                    }  if (false) {
                        HBox testNameAndTypeBox = new HBox(10);
                        testNameAndTypeBox.setAlignment(Pos.CENTER_LEFT);
                        Label testNameLabel = new Label("Test Questionnaire");
                        Label testTypeLabel = new Label("Type QCU");
                        testNameAndTypeBox.getChildren().addAll(testNameLabel, testTypeLabel);

                        VBox questionsBox = new VBox(5);
                        // Fetch the questions for this test and create RadioButtons for them
                        // This will depend on how you have structured your questions
                        // For example:
                        Label question1 = new Label("Question 1");
                        RadioButton choice1 = new RadioButton("choix 1");
                        RadioButton choice2 = new RadioButton("choix 2");
                        RadioButton choice3 = new RadioButton("choix 3");
                        questionsBox.getChildren().addAll(question1, choice1, choice2, choice3);

                        //Qstbox.getChildren().addAll(testNameAndTypeBox, questionsBox);
                    }  if (false) {
                        HBox testNameAndTypeBox = new HBox(10);
                        testNameAndTypeBox.setAlignment(Pos.CENTER_LEFT);
                        Label testTypeLabel = new Label("Serie d'Exercises");
                        testNameAndTypeBox.getChildren().addAll(testTypeLabel);

                        VBox questionsBox = new VBox(5);
                        // Fetch the questions for this test and create the necessary UI elements for them
                        // This will depend on how you have structured your exercises
                        // For example:
                        Label question1 = new Label("Exercice 1");
                        // add text for enonce de l'exercice

                        TextArea answerArea = new TextArea();
                        questionsBox.getChildren().addAll(question1, answerArea);

                        //Qstbox.getChildren().addAll(testNameAndTypeBox, questionsBox);
                    }

                    // Set the font of the content to Montserrat
                    Font montserratFont = new Font("Montserrat", 12); // Replace 12 with your desired font size
                    //Qstbox.setStyle("-fx-font-family: '" + montserratFont.getFamily() + "'; -fx-font-size: " + montserratFont.getSize() + ";");

                    // Add a separator after each question/exercise
                    Separator separator = new Separator();
                    //Qstbox.getChildren().add(separator);

                    //pane.setContent(Qstbox);
                    testsAccordion.getPanes().add(pane);
               /* }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
    }
    private void loadInitialQuestions() {
        // Example initial questions
        String[] Categories = {"Structure familiale", "Dynamique familiale", "Antécédents familiaux"};
        String[] Questions = {"Quelle est la structure de votre famille?", "Comment se passe la dynamique familiale?", "Quels sont les antécédents familiaux?"};

        for (int i = 0; i < Categories.length; i++) {
            addQuestion(Categories[i], Questions[i]);
        }
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

        // Set the font of the labels to Montserrat
        Font montserratFont = new Font("Montserrat", 12); // Replace 12 with your desired font size
        categoryLabel.setFont(montserratFont);
        questionLabel.setFont(montserratFont);

        TextField textField = new TextField();
        textField.setPromptText("Enter answer here");

        questionBox.getChildren().addAll(categoryLabel, questionLabel, textField);

        // Add a separator after each question
        Separator separator = new Separator();
        questionBox.getChildren().add(separator);

        questionContainer.getChildren().add(questionBox);
    }



    private VBox addQuestionQcu(String question, String[] choices) {

        VBox questionsBox = new VBox(5);
        Label question1 = new Label(question);
        for(String choice : choices) {
            RadioButton choice1 = new RadioButton(choice);
            questionsBox.getChildren().add(choice1);
        }
        return questionsBox;
    }

    private VBox addQuestionExercices(String question) {

        VBox questionsBox = new VBox(5);
        Label question1 = new Label(question);
        TextArea answerArea = new TextArea();
        questionsBox.getChildren().add(answerArea);
        return questionsBox;
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
