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

public class LoginController {

    // Non dovrebbero stare nella view? e accedere dal controller via getter?
    @FXML
    private Button btnLogin;

    @FXML
    private Label lblHost;

    @FXML
    private TextField txtHost;

    @FXML
    private TextField txtSid;

    @FXML
    private TextField txtPort;

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private Label lblSid;

    @FXML
    private Label lblPort;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblPassword;

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
            alert.show();

        }

        else{

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Connection established.");
            alert.setHeaderText("Success");
            alert.showAndWait();

            /*
            try{

                // Create new Stage
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/MainMenuView.fxml"));
                Stage stage = new Stage();

                stage.setTitle("Program Menu");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Get info about the stage [ getSource() restituisce un bottone che viene castato a (Node) in modo tale
                // da poter chiamare il metodo .getScene() che restituisce la "Scene" a cui il nodo appartiene.
                // Infine dall'oggetto "Scene" si ricava la "Window" che viene downcastata a "Stage". ]

                // Hide current "Stage"
                ((Node)(event.getSource())).getScene().getWindow().hide();

            }
            */

            try{

                // Change Scene
                // Set the Parent (or Root node) of the scene by loading the FXML file
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/MainMenuView.fxml"));
                // Instantiate a new scene and set the root (FXML file)
                Scene mainMenu = new Scene(root);
                // Get info about the stage [ getSource() restituisce la componente (il bottone) che genera l'evento.
                // Viene castato a "Node" in modo tale da poter chiamare il metodo .getScene() che restituisce la "Scene" a cui il nodo appartiene.
                // Infine dall'oggetto "Scene" si ricava la "Window" che viene downcastata a "Stage". ]
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(mainMenu);
                stage.setResizable(false);
                stage.show();

            }

            catch(IOException e){
                // TODO Handle exceptions
            }


        }

    }

}