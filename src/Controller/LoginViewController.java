package Controller;

import Model.DbConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    // Non dovrebbero stare nella view? e accedere dal controller via getter?
    @FXML private Button btnLogin;
    @FXML private TextField txtHost;
    @FXML private TextField txtSid;
    @FXML private TextField txtPort;
    @FXML private TextField txtUser;
    @FXML private PasswordField pwdPassword;

    @FXML
    void btnLoginOnAction(ActionEvent event){

        String host = txtHost.getText();
        String sid = txtSid.getText();
        String port = txtPort.getText();
        String user = txtUser.getText();
        String password = pwdPassword.getText();


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
                stage.setTitle("Main Menu");
                stage.setScene(new Scene(root));

            }

            catch(IOException e){
                // TODO Handle exceptions
            }

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnLogin.setDefaultButton(true);

    }


}