package Controller.User;

import Model.User;
import Model.UserDAO;
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
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {

    @FXML
    private TableView<User> tblUser;
    @FXML
    private TableColumn<User, String> colUserName;
    @FXML
    private TableColumn<User, String> colFirstName;
    @FXML
    private TableColumn<User, String> colLastName;
    @FXML
    private TableColumn<User, String> colGender;
    @FXML
    private TableColumn<User, LocalDate> colDateOfBirth;
    @FXML
    private TableColumn<User, LocalDate> colDateOfDeath;

    private ObservableList<User> userList;

    private User selectedUser = null;


    @FXML
    private void btnToMainMenuOnAction(ActionEvent event) {

        try {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/MainMenuView.fxml"));
            Scene mainMenu = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            root.requestFocus();
            stage.setScene(mainMenu);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void btnAddUserOnAction(ActionEvent event) {

        showAddUserView();

        updateUserTable();

    }


    @FXML
    private void btnEditUserOnAction(ActionEvent event) {

        if (selectedUser != null) {

            showEditUserView();

        }

        else{

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select a user from the table");
            alert.setHeaderText("User not selected");
            alert.showAndWait();

        }

        updateUserTable();

    }


    @FXML
    private void btnDeleteUserOnAction(ActionEvent event) {

        if (selectedUser != null) {

            // Ask for the user to confirm changes
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm changes");
            confirmAlert.setHeaderText("");
            confirmAlert.setContentText("The user will be deleted. Do you want to proceed?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            if(result.get() == ButtonType.OK){

                try{

                    new UserDAO().delete(selectedUser);

                }
                catch(SQLException e){
                    System.out.println(e.getErrorCode());
                }

            }

        }
        else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select a user from the table");
            alert.setHeaderText("User not selected");
            alert.showAndWait();

        }

        updateUserTable();

    }


    private void showEditUserView() {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/User/UserEditView.fxml"));
            Parent root = fxmlLoader.load();

            UserEditViewController userEditViewController = fxmlLoader.getController();
            userEditViewController.setSelectedUser(selectedUser);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit User");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL); // Modify the modality of the stage to be modal (the user cannot interact with the calling stage)
            stage.showAndWait();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }


    private void showAddUserView(){

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/User/UserAddView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add User");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        }

        catch(IOException e){

            e.printStackTrace();

        }

    }


    /**
     * Fired when the user clicks on the table (selects a row)
     */
    @FXML
    private void tblUserOnMouseClicked(MouseEvent event) {

        selectedUser = tblUser.getSelectionModel().getSelectedItem();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupUserTable();
        updateUserTable();

    }


    private void updateUserTable() {

        // Get all users through the UserDAO
        try {

            userList = new UserDAO().getAll();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        // Set the ObservableList of users as the content of the table
        tblUser.setItems(userList);

    }


    private void setupUserTable() {

        // Setup the columns in the table
        colUserName.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        colGender.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dateOfBirth"));
        colDateOfDeath.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dateOfDeath"));

    }

}