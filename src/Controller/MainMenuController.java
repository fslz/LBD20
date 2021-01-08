package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuController {





        @FXML
        private Pane pnlMainMenu;

        @FXML
        private Button btnUsers;

        @FXML
        private Button btnContacts;

        @FXML
        private Button btnLocations;

        @FXML
        private Button btnSerologicals;

        @FXML
        private Button btnRelationships;

        @FXML
        private Button btnSwabs;

        @FXML
        private Button btnHealthChecks;

        @FXML
        private Button btnBackToLogin;

        @FXML
        void btnBackToLoginOnAction(ActionEvent event) {

                try{
                        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/LoginView.fxml"));
                        Scene mainMenu = new Scene(root);
                        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();window.setScene(mainMenu);
                        window.show();
                }
                catch(IOException e){

                }

        }

}
