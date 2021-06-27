package Controller.User;

import Controller.Contact.ContactsViewController;
import Controller.HealthCheck.HealthChecksViewController;
import Controller.Relationship.RelationshipsViewController;
import Controller.SerologicalTest.SerologicalTestsViewController;
import Controller.Swab.SwabsViewController;
import DAO.*;
import Model.HealthCheck;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UsersViewController implements Initializable {

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
            stage.setTitle("Main Menu");
            stage.setScene(mainMenu);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void btnAddUserOnAction(ActionEvent event) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/User/UserEditView.fxml"));

            // Instantiate the relative controller and assign the swab that needs to be edited.
            UserAddViewController userAddViewController = new UserAddViewController();

            // Assign the controller that needs to be associated with the chosen view.
            fxmlLoader.setController(userAddViewController);

            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add User");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {

            e.printStackTrace();

        }

        updateUserTable();

    }


    @FXML
    private void btnEditUserOnAction(ActionEvent event) {

        // If the user has selected a row.
        if (selectedUser != null) {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/User/UserEditView.fxml"));

                // Instantiate the relative controller and assign the swab that needs to be edited.
                UserEditViewController userEditViewController = new UserEditViewController(selectedUser);

                // Assign the controller that needs to be associated with the chosen view.
                fxmlLoader.setController(userEditViewController);

                Parent root = fxmlLoader.load();

                // Populate components values of the view controller.
                //userEditViewController.setComponentsValues();

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
        // else select a user first.
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select a user from the table");
            alert.setHeaderText("User not selected");
            alert.showAndWait();
        }

        updateUserTable();

    }


    @FXML
    private void cmContactsOnAction(ActionEvent event) {

        try {

            selectedUser.setContactList(new ContactDAOOracleImpl().getAllByUserId(selectedUser)); // Load all contacts of the selected User

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/User/UserContactsView.fxml"));
            Parent root = fxmlLoader.load();

            ContactsViewController contactsViewController = fxmlLoader.getController();
            contactsViewController.setSelectedUser(selectedUser);

            Stage stage = (Stage) tblUser.getScene().getWindow(); // getScene() is only available for components that inherit from Node. A MenuItem does not inherit from Node.

            Scene userContactsView = new Scene(root);
            stage.setTitle("User Contacts");
            root.requestFocus();
            stage.setScene(userContactsView);

        } catch (IOException e) {
            // TODO Handle exceptions
        } catch (SQLException e) {
            // TODO Handle exceptions
        }

    }

    @FXML
    private void cmRelationshipsOnAction(ActionEvent event) {

        // Show Relationships stage for selectedUser
        try {

            selectedUser.setRelationshipList(new RelationshipDAOOracleImpl().getAllByUserId(selectedUser)); // Load all contacts of the selected User

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/User/UserRelationshipsView.fxml"));
            Parent root = fxmlLoader.load();

            RelationshipsViewController relationshipsViewController = fxmlLoader.getController();
            relationshipsViewController.setSelectedUser(selectedUser);

            Stage stage = (Stage) tblUser.getScene().getWindow(); // getScene() is only available for components that inherit from Node. A MenuItem does not inherit from Node.

            Scene userRelationshipsView = new Scene(root);
            stage.setTitle("User Relationships");
            root.requestFocus();
            stage.setScene(userRelationshipsView);

        } catch (IOException e) {
            // TODO Handle exceptions
        } catch (SQLException e) {
            // TODO Handle exceptions
        }

    }

    @FXML
    private void cmSwabsOnAction(ActionEvent event) {

        // Show Swabs stage for selectedUser
        try {

            selectedUser.setSwabList(new SwabDAOOracleImpl().getAllByUserId(selectedUser)); // Load all contacts of the selected User

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Swab/SwabsView.fxml"));
            Parent root = fxmlLoader.load();

            SwabsViewController swabsViewController = fxmlLoader.getController();
            swabsViewController.setSelectedUser(selectedUser);

            Stage stage = (Stage) tblUser.getScene().getWindow(); // getScene() is only available for components that inherit from Node. A MenuItem does not inherit from Node.

            Scene userSwabsView = new Scene(root);
            stage.setTitle("User Swabs");
            root.requestFocus();
            stage.setScene(userSwabsView);

        } catch (IOException e) {
            // TODO Handle exceptions
        } catch (SQLException e) {
            // TODO Handle exceptions
        }

    }

    @FXML
    private void cmSerologicalTestsOnAction(ActionEvent event) {

        // Show Serological Tests stage for selectedUser.
        try {

            selectedUser.setSerologicalTestList(new SerologicalTestDAOOracleImpl().getAllByUserId(selectedUser)); // Load all contacts of the selected User

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/SerologicalTest/SerologicalTestsView.fxml"));
            Parent root = fxmlLoader.load();

            SerologicalTestsViewController serologicalTestsViewController = fxmlLoader.getController();
            serologicalTestsViewController.setSelectedUser(selectedUser);

            Stage stage = (Stage) tblUser.getScene().getWindow(); // getScene() is only available for components that inherit from Node. A MenuItem does not inherit from Node.

            Scene userSerologicalTestsView = new Scene(root);
            stage.setTitle("User Serological Tests");
            root.requestFocus();
            stage.setScene(userSerologicalTestsView);

        } catch (IOException e) {
            // TODO Handle exceptions
        } catch (SQLException e) {
            // TODO Handle exceptions
        }

    }

    @FXML
    private void cmHealthChecksOnAction(ActionEvent event) {

        // Show Health Checks stage for selectedUser.
        try {

            List<HealthCheck> healthCheckList = new HealthCheckDAOOracleImpl().getAllByUserId(selectedUser);
            selectedUser.setHealthCheckList(new HealthCheckDAOOracleImpl().getAllByUserId(selectedUser)); // Load all health checks of the selected User

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/HealthCheck/HealthChecksView.fxml"));
            Parent root = fxmlLoader.load();

            HealthChecksViewController healthChecksViewController = fxmlLoader.getController();
            healthChecksViewController.setSelectedUser(selectedUser);

            Stage stage = (Stage) tblUser.getScene().getWindow(); // getScene() is only available for components that inherit from Node. A MenuItem does not inherit from Node.

            Scene userHealthChecksView = new Scene(root);
            stage.setTitle("User Health Checks.");
            root.requestFocus();
            stage.setScene(userHealthChecksView);

        } catch (IOException e) {
            // TODO Handle exceptions
        } catch (SQLException e) {
            // TODO Handle exceptions
        }

    }

    @FXML
    private void btnDeleteUserOnAction(ActionEvent event) {

        if (selectedUser != null) {

            // Ask for the user to confirm changes.
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm changes");
            confirmAlert.setHeaderText("");
            confirmAlert.setContentText("The user will be deleted. Do you want to proceed?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.get() == ButtonType.OK) {

                try {

                    new UserDAOOracleImpl().delete(selectedUser);

                } catch (SQLException e) {
                    System.out.println(e.getErrorCode());
                }

            }

        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select a user from the table");
            alert.setHeaderText("User not selected");
            alert.showAndWait();

        }

        updateUserTable();

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
        selectedUser = null;
    }


    private void updateUserTable() {

        // Get all users through the UserDAOOracleImpl
        try {

            userList = new UserDAOOracleImpl().getAll();

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