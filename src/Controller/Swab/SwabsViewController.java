package Controller.Swab;

import DAO.SwabDAOOracleImpl;
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

public class SwabsViewController implements Initializable {

    private User selectedUser = null;
    private Swab selectedSwab = null;
    private ObservableList<Swab> swabList;

    @FXML
    private TableView<Swab> tblUserSwabs;
    @FXML
    private TableColumn<Swab, String> colPositivity;
    @FXML
    private TableColumn<Swab, LocalDateTime> colDateResult;
    @FXML
    private Button btnToUsers;
    @FXML
    private Button btnAddUserSwab;
    @FXML
    private Button btnEditUserSwab;
    @FXML
    private Button btnDeleteUserSwab;


    @FXML
    void btnAddUserSwabOnAction(ActionEvent event) {

        Swab swab = new Swab();

        // If selectedUser == null -> open "select user" scene

        swab.setUser(selectedUser);

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Swab/SwabEditView.fxml"));
            //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Swab/SwabAddPropertiesView.fxml"));
            Parent root = fxmlLoader.load();

            // Access controller
            SwabEditViewController swabEditViewController = fxmlLoader.getController();
            //SwabAddPropertiesViewController swabAddPropertiesViewController = fxmlLoader.getController();
            // Set contact
            //swabAddPropertiesViewController.setSwab(swab);
            swabEditViewController.setSwab(swab);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Enter swab info");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {

            e.printStackTrace();

        }

        updateSwabTable();

    }


    @FXML
    void btnEditUserSwabOnAction(ActionEvent event) {

        if (selectedSwab != null) {

            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Swab/SwabEditView.fxml"));
                Parent root = fxmlLoader.load();

                SwabEditViewController swabEditViewController = fxmlLoader.getController();

                // Swab to edit.
                swabEditViewController.setSwab(selectedSwab);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Edit swab");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL); // Modify the modality of the stage to be modal (the user cannot interact with the calling stage)
                stage.showAndWait();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        else{

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select a swab from the table");
            alert.setHeaderText("Swab not selected");
            alert.showAndWait();

        }

        updateSwabTable();

    }


    @FXML
    void btnDeleteUserSwabOnAction(ActionEvent event) {

        if (selectedSwab != null) {

            // Ask for the user to confirm changes
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm changes");
            confirmAlert.setHeaderText("");
            confirmAlert.setContentText("The swab will be deleted. Do you want to proceed?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            if(result.get() == ButtonType.OK){

                try{

                    new SwabDAOOracleImpl().delete(selectedSwab);

                }
                catch(SQLException e){
                    System.out.println(e.getErrorCode());
                }

            }

        }
        else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select a swab from the table");
            alert.setHeaderText("Swab not selected");
            alert.showAndWait();

        }

        updateSwabTable();

    }





    @FXML
    void tblUserSwabsOnMouseClicked(MouseEvent event) {

        this.selectedSwab = tblUserSwabs.getSelectionModel().getSelectedItem();

    }


    // Back to Users List
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


    public void setSelectedUser(User selectedUser) {

        // Set the user instance
        this.selectedUser = selectedUser;

        // Set the ObservableList of users as the content of the table
        tblUserSwabs.setItems((ObservableList<Swab>) selectedUser.getSwabList());

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupUserTable();

    }


    private void updateSwabTable() {

        // Get all users through the SwabDAOOracleImpl
        try {

            selectedUser.setSwabList(new SwabDAOOracleImpl().getAllByUserId(selectedUser)) ;

            // Set the ObservableList of users as the content of the table
            tblUserSwabs.setItems((ObservableList<Swab>) selectedUser.getSwabList());

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }


    private void setupUserTable() {

        // Setup the columns in the table
        colPositivity.setCellValueFactory(new PropertyValueFactory<Swab, String>("positivity"));
        colDateResult.setCellValueFactory(new PropertyValueFactory<Swab, LocalDateTime>("dateResult"));

    }


}
