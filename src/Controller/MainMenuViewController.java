package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuViewController {

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

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/UsersView.fxml"));
                        Parent root = fxmlLoader.load();
                        Scene mainMenu = new Scene(root);
                        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        stage.setScene(mainMenu);
                        stage.setTitle("Users");
                        root.requestFocus();

                }
                catch(IOException e){

                }
        }

}
