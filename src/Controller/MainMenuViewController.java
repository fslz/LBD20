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


        @FXML
        void btnUsersOnAction(ActionEvent event) {

                try{    // Load Users Menu scene in the current stage

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/UsersView.fxml"));
                        Parent root = fxmlLoader.load();
                        Scene usersMenu = new Scene(root);
                        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        stage.setScene(usersMenu);
                        stage.setTitle("Users");
                        root.requestFocus();

                }
                catch(IOException e){

                        e.printStackTrace();

                }
        }

        @FXML
        void btnLocationsOnAction(ActionEvent event){

                try{    // Load Locations Menu scene in the current stage

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/LocationsView.fxml"));
                        Parent root = fxmlLoader.load();
                        Scene locationsMenu = new Scene(root);
                        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        stage.setScene(locationsMenu);
                        stage.setTitle("Locations");
                        root.requestFocus();

                }
                catch(IOException e){

                        e.printStackTrace();

                }

        }

}
