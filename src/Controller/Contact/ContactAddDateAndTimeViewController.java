package Controller.Contact;

import DAO.ContactDAOOracleImpl;
import Model.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
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
            contact.setDateReceived(localDate);

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
                        errorAlert.setContentText("This contact is happening before the date of birth of one, or of both users.");
                        errorAlert.showAndWait();
                    }
                    // CHECK date of death trigger exception raised
                    if (e.getErrorCode() == 20002) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("This contact is happening after the date of death of one, or of both users.");
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

    }
}
