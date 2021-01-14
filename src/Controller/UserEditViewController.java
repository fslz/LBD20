package Controller;

import Model.User;
import Model.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserEditViewController implements Initializable {

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private DatePicker dpDateOfBirth;
    @FXML
    private DatePicker dpDateOfDeath;
    @FXML
    private Label lblUserName;
    @FXML
    private Label lblFirstName;
    @FXML
    private Label lblLastName;
    @FXML
    private Label lblDateOfBirth;
    @FXML
    private Label lblDateOfDeath;
    @FXML
    private ChoiceBox<String> cbGender;
    @FXML
    private Label lblGender;


    private User selectedUser;

    private ObservableList<String> availableChoices = FXCollections.observableArrayList("M", "F");


    @FXML
    void btnCancelOnAction(ActionEvent event) {

        // Get the stage the button pressed belongs to
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // Close it
        stage.close();

    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {

        if (validation()) { // validation() checks if all the required textfields are filled

            // Ask for the user to confirm changes
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm changes");
            confirmAlert.setHeaderText("");
            confirmAlert.setContentText("Apply changes?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            // If user confirms
            if (result.get() == ButtonType.OK) {
                // Then apply changes to the user on the db through the DAOOracle
                try {

                    new UserDAO().update(
                            new User(selectedUser.getId(),
                                    txtUserName.getText(),
                                    txtFirstName.getText(),
                                    txtLastName.getText(),
                                    cbGender.getValue(),
                                    dpDateOfBirth.getValue(),
                                    dpDateOfDeath.getValue()
                            )
                    );

                    // User successfully edited. Show alert.
                    Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    errorAlert.setContentText("User successfully edited.");
                    errorAlert.showAndWait();

                    // Close the UserEditView stage
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();


                } catch (SQLException e) {

                    // UNIQUE constraint violation
                    if (e.getErrorCode() == 1) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("A user with this username already exists.");
                        errorAlert.showAndWait();
                    }
                    // CHECK date constraint violation
                    if (e.getErrorCode() == 2290) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("The date of death must be greater than the date of birth.");
                        errorAlert.showAndWait();
                    }

                }

            }
            else {
                // Else close the confirmation dialog
                confirmAlert.close();

            }

        }

    }


    public void setSelectedUser(User selectedUser) {

        this.selectedUser = selectedUser;

        setComponentsValues();

    }


    private void setComponentsValues() {

        txtFirstName.setText(selectedUser.getFirstName());
        txtLastName.setText(selectedUser.getLastName());
        txtUserName.setText(selectedUser.getUserName());
        cbGender.setValue(selectedUser.getGender());
        dpDateOfBirth.setValue(selectedUser.getDateOfBirth());
        dpDateOfDeath.setValue(selectedUser.getDateOfDeath());

    }


    private boolean validation() {

        boolean isValid = true;
        StringBuilder errorMsg = new StringBuilder();

        if (txtUserName.getText() == null || txtUserName.getText().trim().isEmpty()) {
            errorMsg.append("- Please enter a username.\n");
            isValid = false;
        }
        if (txtFirstName.getText() == null || txtFirstName.getText().trim().isEmpty()) {
            errorMsg.append("- Please enter a first name.\n");
            isValid = false;
        }
        if (txtLastName.getText() == null || txtLastName.getText().trim().isEmpty()) {
            errorMsg.append("- Please enter a last name.\n");
            isValid = false;
        }
        if (cbGender.getValue() == null) {
            errorMsg.append("- Please select a gender.\n");
            isValid = false;
        }
        if (dpDateOfBirth.getValue() == null) {
            errorMsg.append("- Please enter the date of birth.\n");
            isValid = false;
        }

        if (!isValid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Missing Information");
            alert.setContentText(errorMsg.toString());
            alert.showAndWait();
        }

        return isValid;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cbGender.setItems(availableChoices);

    }

}
