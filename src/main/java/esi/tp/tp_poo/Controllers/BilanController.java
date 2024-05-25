package esi.tp.tp_poo.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class BilanController {
    @FXML
    private VBox questionContainer;

    @FXML
    private VBox testsVBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button seDeconnecterButton;

    //private List<TestController> testControllers = new ArrayList<>();

    @FXML
    public void initialize() {
        // This method is called after the FXML file has been loaded
        loadInitialQuestions();
        seDeconnecterButton.setOnAction(this::handleSeDeconnecterButtonAction);
       // addTest();
    }
    @FXML
    private void handleSaveButton() {
        // Handle save button action
        // This method will be called when the save button is clicked
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
}
