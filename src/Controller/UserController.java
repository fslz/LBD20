package Controller;

import Model.User;
import Model.UserDAO;
import com.sun.org.apache.xpath.internal.operations.String;
import javafx.beans.property.SimpleStringProperty;
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
import sun.java2d.pipe.SpanShapeRenderer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class UserController implements Initializable {


    @FXML
    private Label lblFirstName;
    @FXML
    private Label lblLastName;
    @FXML
    private Label lblGender;
    @FXML
    private Label lblDateOfBirth;
    @FXML
    private Label lblDateOfDeath;
    @FXML
    private DatePicker dpDateOfBirth;
    @FXML
    private RadioButton rbMale;
    @FXML
    private RadioButton rbFemale;
    @FXML
    private ToggleGroup genderToggleGroup;
    @FXML
    private DatePicker dpDateOfDeath;
    @FXML
    private Button btnToMainMenu;
    @FXML
    private Button btnAddUser;
    @FXML
    private Button btnEditUser;
    @FXML
    private Button btnDeleteUser;
    @FXML
    private Button btnAddRelationship;
    @FXML
    private TableView<User> tblUser;
    @FXML
    private TableColumn<User, SimpleStringProperty> colFirstName;
    @FXML
    private TableColumn<User, SimpleStringProperty> colLastName;
    @FXML
    private TableColumn<User, SimpleStringProperty> colGender;
    @FXML
    private TableColumn<User, LocalDate> colDateOfBirth;
    @FXML
    private TableColumn<User, LocalDate> colDateOfDeath;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtUserName;
    @FXML
    private Label lblUserName;
    @FXML
    private TableColumn<User, SimpleStringProperty> colUserName;

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

        }

    }

    @FXML
    private void btnAddUserOnAction(ActionEvent event) {




        updateUserTable();

    }

    @FXML
    private void btnDeleteUserOnAction(ActionEvent event) {

        if (selectedUser != null) {

            new UserDAO().deleteUser(selectedUser);

        }
        else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a user from the table");
            alert.showAndWait();

        }

        updateUserTable();

    }

    @FXML
    private void btnEditUserOnAction(ActionEvent event) {

        if (selectedUser != null) {

            showUserEditView();

        }

        updateUserTable();

    }

    private void showUserEditView() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserEditView.fxml"));
            Parent root = loader.load();
            UserEditController userEditController = loader.getController(); // This did the "trick"
            userEditController.setSelectedUser(selectedUser); // Passing the client-object to the ClientViewController
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit User");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL); // Modify the modality of the stage to be modal (cannot interact with the calling stage)
            stage.showAndWait();

        } catch (IOException e) {
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
        userList = new UserDAO().getUsersAll();
        //selectedUser = null;
        // Set the ObservableList of users as the content of the table
        tblUser.setItems(userList);

    }

    private void setupUserTable() {

        // Setup the columns in the table
        colUserName.setCellValueFactory(new PropertyValueFactory<User, SimpleStringProperty>("userName"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<User, SimpleStringProperty>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<User, SimpleStringProperty>("lastName"));
        colGender.setCellValueFactory(new PropertyValueFactory<User, SimpleStringProperty>("gender"));
        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dateOfBirth"));
        colDateOfDeath.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dateOfDeath"));

    }

}
