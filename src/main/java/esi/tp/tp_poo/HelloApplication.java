package esi.tp.tp_poo;

import esi.tp.tp_poo.Models.Adult;
import esi.tp.tp_poo.Models.BilanOrthophonique;
import esi.tp.tp_poo.Models.Orthophoniste;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 293, 164);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Orthophoniste orthophoniste = new Orthophoniste("moh", "islam", "kk", "0790750457", "marafislam67@gmail.com", "kkk");
        try {
            Adult patient1 = orthophoniste.addPatientAdult("kk", "0690045793", "kk", "lll", "islam", "aa");
            Adult patient2 = orthophoniste.addPatientAdult("kk", "0690045793", "kk", "lll", "islam", "aa");
            Adult patient3 = orthophoniste.addPatientAdult("kk", "0690045793", "kk", "lll", "islam", "aa");

            System.out.println("i alll " + patient1.getPatient_id());
            orthophoniste.CreateBilan(patient1.getPatient_id());
            orthophoniste.CreateBilan(patient1.getPatient_id());
            List<BilanOrthophonique> bilans = BilanOrthophonique.getBilansForOrthophonisteWithPatient(orthophoniste.getIdentifiant(), patient1.getPatient_id());
            for (BilanOrthophonique bilan : bilans) {
                System.out.println("id " + bilan.getBilanId() + " ortho " + bilan.getIdOrthophoniste());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        launch();
    }
}