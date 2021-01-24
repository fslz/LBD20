package Controller.Contact;

import DAO.ContactDAOOracleImpl;
import DAO.UserDAOOracleImpl;
import Model.Contact;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class ContactAddDateAndTimeViewController implements Initializable {


    @FXML
    private DatePicker dpContactDate;
    @FXML
    private Spinner<Integer> spContactHours;
    @FXML
    private Spinner<Integer> spContactSeconds;
    @FXML
    private Spinner<Integer> spContactMinutes;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnCancel;

    private Contact contact = null;

    private LocalDateTime localDateTime = null;

    @FXML
    void btnCancelOnAction(ActionEvent event) {

        contact = null;
        // Get the stage the button pressed belongs to
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // Close it
        stage.close();

    }

    @FXML
    void btnNextOnAction(ActionEvent event) {

        if (validation()) { // validation() checks if all the required textfields are filled

            LocalDate localDate = dpContactDate.getValue();
            LocalTime localTime = LocalTime.of(spContactHours.getValue(), spContactMinutes.getValue(), spContactSeconds.getValue());
            localDateTime = LocalDateTime.of(localDate, localTime);
            contact.setDateReceived(localDateTime);

            // Ask for the user to confirm changes
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm changes");
            confirmAlert.setContentText("The new contact will be created. Are you sure you want to proceed?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            // If user confirms
            if (result.get() == ButtonType.OK) {
                // Then then the new contact will be added on the db through the DAOOracle
                try {

                    new ContactDAOOracleImpl().create(contact);

                    // Contact successfully added. Show alert.
                    Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    errorAlert.setContentText("Contact successfully created.");
                    errorAlert.showAndWait();

                    // Close the UserInsertView stage
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();


                } catch (SQLException e) {

                    // UNIQUE constraint violation
                    if (e.getErrorCode() == 1) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("One, or both, of the users already participate in a contact in that moment. Please check your date and time.");
                        errorAlert.showAndWait();
                    }
                    // CHECK date of birth trigger exception raised
                    if (e.getErrorCode() == 20001) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("This contact is happening before the date of birth of one, or of both the users.");
                        errorAlert.showAndWait();
                    }
                    // CHECK date of death trigger exception raised
                    if (e.getErrorCode() == 20002) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("This contact is happening after the date of death of one, or of both the users.");
                        errorAlert.showAndWait();
                    }

                }

            } else {
                // Else close the confirmation dialog
                confirmAlert.close();

            }

        }
    }


    private boolean validation() {

        boolean isValid = true;
        StringBuilder errorMsg = new StringBuilder();

        if (dpContactDate.getValue() == null) {
            errorMsg.append("- Please select a valid date.\n");
            isValid = false;
        }

        if (spContactHours.getValue() == null || spContactHours.getValue() > 23 || spContactHours.getValue() < 0) {
            errorMsg.append("- Please enter a valid value for the hours.\n");
            isValid = false;
        }

        if (spContactMinutes.getValue() == null || spContactMinutes.getValue() > 59 || spContactMinutes.getValue() < 0) {
            errorMsg.append("- Please enter a valid value for the minutes.\n");
            isValid = false;
        }

        if (spContactSeconds.getValue() == null || spContactSeconds.getValue() > 59 || spContactSeconds.getValue() < 0) {
            errorMsg.append("- Please enter a valid value for the seconds.\n");
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


    public void setContact(Contact contact) {

        this.contact = contact;

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SpinnerValueFactory<Integer> hoursSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        this.spContactHours.setValueFactory(hoursSpinnerValueFactory);
        SpinnerValueFactory<Integer> minutesSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        this.spContactMinutes.setValueFactory(minutesSpinnerValueFactory);
        SpinnerValueFactory<Integer> secondsSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        this.spContactSeconds.setValueFactory(secondsSpinnerValueFactory);

    }
}
