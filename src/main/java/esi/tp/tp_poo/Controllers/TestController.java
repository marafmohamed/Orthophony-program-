package esi.tp.tp_poo.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class TestController {

    @FXML
    private VBox questionBox;

    @FXML
    private Button SubmitButton;

    @FXML
    private Button addQuestion;

    @FXML
    private TextField NomField;

    @FXML
    private TextField CapacitéField;

    @FXML
    private ComboBox typecombo;

    @FXML
    public void initialize() {
        // Add action to the addQuestion button
        addQuestion.setOnAction(event -> {
            TextField newQuestionField = new TextField();
            newQuestionField.setPromptText("Enter question here...");
            HBox hBox = new HBox(newQuestionField);
            questionBox.getChildren().add(hBox);
        });
        SubmitButton.setOnAction(this::handleSubmitButtonAction);
    }
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        // Validate that all fields are filled
        if (NomField.getText().isEmpty() || CapacitéField.getText().isEmpty() || typecombo.getValue() == null) {
            showAlert("Please fill all fields");
            return;
        }

        // Validate that all questions have been answered
        for (int i = 1; i < questionBox.getChildren().size(); i++) {
            HBox hBox = (HBox) questionBox.getChildren().get(i);
            TextField questionField = (TextField) hBox.getChildren().get(0);
            if (questionField.getText().isEmpty()) {
                showAlert("Please fill all questions");
                return;
            }
        }

        // Prepare data for insertion into the database
        String nom = NomField.getText();
        String capacite = CapacitéField.getText();
        String type = (String) typecombo.getValue();
        List<String> questions = new ArrayList<>();
        for (int i = 1; i < questionBox.getChildren().size(); i++) {
            HBox hBox = (HBox) questionBox.getChildren().get(i);
            TextField questionField = (TextField) hBox.getChildren().get(0);
            questions.add(questionField.getText());
        }

        // TODO: Add your database insertion logic here
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}