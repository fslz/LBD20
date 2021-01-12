package Controller;

import javafx.beans.property.SimpleStringProperty;
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

        @FXML private Pane pnlMainMenu;
        @FXML private Button btnUsers;
        @FXML private Button btnContacts;
        @FXML private Button btnLocations;
        @FXML private Button btnSerologicals;
        @FXML private Button btnRelationships;
        @FXML private Button btnSwabs;
        @FXML private Button btnHealthChecks;

        @FXML
        void btnUsersOnAction(ActionEvent event) {

                try{    // Load next scene (Main Menu) in the current stage

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/UserView.fxml"));
                        Parent root = fxmlLoader.load();
                        Scene mainMenu = new Scene(root);
                        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        stage.setScene(mainMenu);
                        root.requestFocus();

                }
                catch(IOException e){

                }
        }

}
