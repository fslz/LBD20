package Controller.User;

import Controller.SerologicalTest.SerologicalTestAddPropertiesViewController;
import Controller.Swab.SwabAddPropertiesViewController;
import DAO.SerologicalTestDAOOracleImpl;
import DAO.SwabDAOOracleImpl;
import Model.SerologicalTest;
import Model.Swab;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserSerologicalTestsViewController implements Initializable {

    @FXML
    private TableView<SerologicalTest> tblUserSerologicalTest;
    @FXML
    private TableColumn<SerologicalTest, String> colIgM;
    @FXML
    private TableColumn<SerologicalTest, String> colIgG;
    @FXML
    private TableColumn<SerologicalTest, LocalDateTime> colDateResult;
    @FXML
    private Button btnToUsers;
    @FXML
    private Button btnAddUserSerologicalTest;
    @FXML
    private Button btnEditUserSerologicalTest;
    @FXML
    private Button btnDeleteUserSerologicalTest;


    private User selectedUser = null;
    private SerologicalTest selectedSerologicalTest = null;
    private ObservableList<SerologicalTest> serologicalTestList;


    @FXML
    void btnAddUserSerologicalTestOnAction(ActionEvent event) {

        SerologicalTest serologicalTest = new SerologicalTest();

        // If selectedUser == null -> open "select user" scene

        serologicalTest.setUser(selectedUser);

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/SerologicalTest/SerologicalTestAddPropertiesView.fxml"));
            Parent root = fxmlLoader.load();

            // Access controller
            SerologicalTestAddPropertiesViewController serologicalTestAddPropertiesViewController = fxmlLoader.getController();
            // Set contact
            serologicalTestAddPropertiesViewController.setSerologicalTest(serologicalTest);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Enter serological test informations");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {

            e.printStackTrace();

        }

        updateSerologicalTestTable();

    }

    @FXML
    void btnDeleteUserSerologicalTestOnAction(ActionEvent event) {

        if (selectedSerologicalTest != null) {

            // Ask for the user to confirm changes
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm changes");
            confirmAlert.setHeaderText("");
            confirmAlert.setContentText("The serological test will be deleted. Do you want to proceed?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            if(result.get() == ButtonType.OK){

                try{

                    new SerologicalTestDAOOracleImpl().delete(selectedSerologicalTest);

                }
                catch(SQLException e){
                    System.out.println(e.getErrorCode());
                }

            }

        }
        else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select a serological test from the table");
            alert.setHeaderText("Serological test not selected");
            alert.showAndWait();

        }

        updateSerologicalTestTable();

    }

    @FXML
    void btnEditUserSerologicalTestOnAction(ActionEvent event) {

    }


    @FXML
    void btnToUsersOnAction(ActionEvent event) {

        try {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/User/UsersView.fxml"));
            Scene usersView = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            root.requestFocus();
            stage.setScene(usersView);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void tblUserSerologicalTestOnMouseClicked(MouseEvent event) {

        this.selectedSerologicalTest = tblUserSerologicalTest.getSelectionModel().getSelectedItem();

    }


    public void setSelectedUser(User selectedUser) {

        // Set the user instance
        this.selectedUser = selectedUser;

        // Set the ObservableList of users as the content of the table
        tblUserSerologicalTest.setItems((ObservableList<SerologicalTest>) selectedUser.getSerologicalTestList());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupUserTable();

    }


    private void updateSerologicalTestTable() {

        // Get all users through the SwabDAOOracleImpl
        try {

            selectedUser.setSerologicalTestList(new SerologicalTestDAOOracleImpl().getAllByUserId(selectedUser)) ;

            // Set the ObservableList of users as the content of the table
            tblUserSerologicalTest.setItems((ObservableList<SerologicalTest>) selectedUser.getSerologicalTestList());

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    private void setupUserTable() {

        // Setup the columns in the table
        colIgM.setCellValueFactory(new PropertyValueFactory<SerologicalTest, String>("igm"));
        colIgG.setCellValueFactory(new PropertyValueFactory<SerologicalTest, String>("igg"));
        colDateResult.setCellValueFactory(new PropertyValueFactory<SerologicalTest, LocalDateTime>("dateResult"));

    }
}
