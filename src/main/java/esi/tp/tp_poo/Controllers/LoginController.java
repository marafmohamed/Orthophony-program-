package esi.tp.tp_poo.Controllers;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController /*implements Initializable */{

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label erreurText;
    @FXML
    private TextField pseudo;
    @FXML
    private PasswordField motDePasse;

    //    checked and updated
    @FXML
    void nvCompteButton(ActionEvent event) {
       /* FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Inscription.fxml"));
        scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load FXML file");
        }

        Button button = (Button) event.getSource();
        stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.show();*/
    }

    //    checked and updated
    @FXML
    void seConnecterButton(ActionEvent event) throws /*UserNotFoundException,*/ IOException {
        /*String userPseudo = pseudo.getText();
        String userMdp = motDePasse.getText();
        if (myDesktopPlanner.isExist(userPseudo))
        {
            Utilisateur user = myDesktopPlanner.findUser(userPseudo,userMdp);
            if (user != null)
            {
                System.out.println("Connection valide");
                utilisateurCourant = myDesktopPlanner.findUser(userPseudo,userMdp);

                //changer the stage
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Calendar.fxml"));
                scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Couldn't load FXML file");
                }

                Button button = (Button) event.getSource();
                stage = (Stage) button.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
            else
            {
                System.out.println("mdp incorrect");
                connexionInvalid();
            }
        }
        else {
            System.out.println("seConnecterButton func n'a pas trouvé l'utilisateur!");
            connexionInvalid();
        }
        */

    }

    public void connexionInvalid() {
        erreurText.setText("Pseudo ou mot de passe invalide!");
    }

}
