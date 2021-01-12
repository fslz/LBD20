package Controller;

import Model.DbConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
// NEW
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginViewController {

    // Non dovrebbero stare nella view? e accedere dal controller via getter?
    @FXML private Button btnLogin;
    @FXML private Label lblHost;
    @FXML private TextField txtHost;
    @FXML private TextField txtSid;
    @FXML private TextField txtPort;
    @FXML private TextField txtUser;
    @FXML private PasswordField pwdPassword;
    @FXML private Label lblSid;
    @FXML private Label lblPort;
    @FXML private Label lblUser;
    @FXML private Label lblPassword;

    @FXML
    void btnLoginOnAction(ActionEvent event){

        String host = txtHost.getText();
        String sid = txtSid.getText();
        String port = txtPort.getText();
        String user = txtUser.getText();
        //String password = pwdPassword.getText();
        String password = "yum3jiva0";

        Connection connection = new DbConnector().getConnection(host, sid, port, user, password);

        if(connection == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Connection failed.");
            alert.setHeaderText("Error");
            alert.showAndWait();

        }

        else{

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Connection established.");
            alert.setHeaderText("Success");
            alert.showAndWait();

            try{

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainMenuView.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Main Menu");

            }

            catch(IOException e){
                // TODO Handle exceptions
            }


        }

    }

}