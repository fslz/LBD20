package Controller.User;

import Model.User;
import DAO.UserDAOOracleImpl;
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

public class UserAddViewController implements Initializable {

    @FXML
    private Button btnCancel;
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
    private ChoiceBox<String> cbGender;


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
            confirmAlert.setContentText("The new user will be created. Are you sure you want to proceed?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            // If user confirms
            if (result.get() == ButtonType.OK) {
                // Then then the new user will be added on the db through the DAOOracle
                try {

                    new UserDAOOracleImpl().create(
                            new User(0,
                                    txtUserName.getText(),
                                    txtFirstName.getText(),
                                    txtLastName.getText(),
                                    cbGender.getValue(),
                                    dpDateOfBirth.getValue(),
                                    dpDateOfDeath.getValue()
                            )
                    );

                    // User successfully added. Show alert.
                    Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    errorAlert.setContentText("User successfully created.");
                    errorAlert.showAndWait();

                    // Close the UserInsertView stage
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
            errorMsg.append("- Please select gender.\n");
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