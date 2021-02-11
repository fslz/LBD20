package Controller.Swab;

import DAO.SwabDAOOracleImpl;
import Model.Swab;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class SwabEditViewController implements Initializable {


    private LocalDateTime localDateTime = null;
    private Swab swab = null;
    private ObservableList<String> availableChoices = FXCollections.observableArrayList("positive", "negative");

    @FXML
    private ChoiceBox<String> cbPositivity;
    @FXML
    private Label lblPositivity;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private DatePicker dpSwabDate;



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

            swab.setPositivity(cbPositivity.getValue());

            LocalDate localDate = dpSwabDate.getValue();
            swab.setDateResult(localDate);

            // Ask for the user to confirm changes
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm changes");
            confirmAlert.setContentText("The swab will be modified. Are you sure you want to proceed?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            // If user confirms
            if (result.get() == ButtonType.OK) {
                // Then then the new contact will be added on the db through the DAOOracle
                try {

                    new SwabDAOOracleImpl().update(swab);

                    // Contact successfully added. Show alert.
                    Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    errorAlert.setContentText("Swab successfully modified.");
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
                        errorAlert.setContentText("The date of death of the user is more than the date of the swab.");
                        errorAlert.showAndWait();
                    }
                    // CHECK date of death trigger exception raised
                    if (e.getErrorCode() == 20002) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("The date of death of the user is less than the date of the swab.");
                        errorAlert.showAndWait();
                    }

                }

            } else {
                // Else close the confirmation dialog
                confirmAlert.close();

            }

        }

    }


    public void setSwab(Swab swab) {

        this.swab = swab;

        setComponentsValues();

    }


    private void setComponentsValues() {


        cbPositivity.setValue(swab.getPositivity());

        dpSwabDate.setValue(swab.getDateResult());

    }


    private boolean validation() {

        boolean isValid = true;
        StringBuilder errorMsg = new StringBuilder();

        if (dpSwabDate.getValue() == null) {
            errorMsg.append("- Please select a valid date.\n");
            isValid = false;
        }

        if(cbPositivity.getValue() == null) {
            errorMsg.append("- Please choose positivity.\n");
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

        cbPositivity.setItems(availableChoices);

    }






















}
