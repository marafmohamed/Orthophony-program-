package esi.tp.tp_poo;

import esi.tp.tp_poo.Models.Adult;
import esi.tp.tp_poo.Models.BilanOrthophonique;
import esi.tp.tp_poo.Models.Orthophoniste;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Views/Login.fxml"));
        // Create the root AnchorPane from FXML
        BorderPane root = fxmlLoader.load();

        // Create a scene with the root AnchorPane
        Scene scene = new Scene(root);

        // Set the scene to the primary stage
        primaryStage.setScene(scene);

        // Set the title of the window
        primaryStage.setTitle("Orthophoniste Application");

        // Show the window
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}