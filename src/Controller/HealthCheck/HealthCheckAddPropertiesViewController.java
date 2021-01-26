package Controller.HealthCheck;

import DAO.HealthCheckDAOOracleImpl;
import DAO.SerologicalTestDAOOracleImpl;
import Model.HealthCheck;
import Model.SerologicalTest;
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

public class HealthCheckAddPropertiesViewController implements Initializable {


    @FXML
    private ChoiceBox<String> cbFever;
    @FXML
    private ChoiceBox<String> cbSmellTasteDIsorders;
    @FXML
    private Label lblFever;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private DatePicker dpContactDate;
    @FXML
    private Spinner<Integer> spContactHours;
    @FXML
    private Spinner<Integer> spContactSeconds;
    @FXML
    private Spinner<Integer> spContactMinutes;
    @FXML
    private ChoiceBox<String> cbRespiratoryDisorders;
    @FXML
    private Label lblRespiratoryDisorders;

    @FXML
    private Label lblSmellTasteDIsorders;


    private LocalDateTime localDateTime = null;
    private HealthCheck healthCheck = null;
    private ObservableList<String> availableChoices = FXCollections.observableArrayList("Y", "N");


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

            healthCheck.setFever(cbFever.getValue());
            healthCheck.setRespiratoryDisorder(cbRespiratoryDisorders.getValue());
            healthCheck.setSmellTasteDisorder(cbSmellTasteDIsorders.getValue());

            LocalDate localDate = dpContactDate.getValue();
            LocalTime localTime = LocalTime.of(spContactHours.getValue(), spContactMinutes.getValue(), spContactSeconds.getValue());
            localDateTime = LocalDateTime.of(localDate, localTime);
            healthCheck.setDateOfCheck(localDateTime);

            // Ask for the user to confirm changes
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm changes");
            confirmAlert.setContentText("The new health check will be created. Are you sure you want to proceed?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            // If user confirms
            if (result.get() == ButtonType.OK) {
                // Then then the new health check will be added on the db through the DAOOracle
                try {

                    new HealthCheckDAOOracleImpl().create(healthCheck);

                    // health check successfully added. Show alert.
                    Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    errorAlert.setContentText("Health Check test successfully created.");
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

        if(cbFever.getValue() == null) {
            errorMsg.append("- Please select a value for Fever.\n");
            isValid = false;
        }

        if(cbRespiratoryDisorders.getValue() == null) {
            errorMsg.append("- Please select a value for Respiratory Disorders.\n");
            isValid = false;
        }

        if(cbSmellTasteDIsorders.getValue() == null) {
            errorMsg.append("- Please select a value for Smell/Taste Disorder.\n");
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

        cbFever.setItems(availableChoices);
        cbRespiratoryDisorders.setItems(availableChoices);
        cbSmellTasteDIsorders.setItems(availableChoices);

        SpinnerValueFactory<Integer> hoursSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        this.spContactHours.setValueFactory(hoursSpinnerValueFactory);
        SpinnerValueFactory<Integer> minutesSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        this.spContactMinutes.setValueFactory(minutesSpinnerValueFactory);
        SpinnerValueFactory<Integer> secondsSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        this.spContactSeconds.setValueFactory(secondsSpinnerValueFactory);

    }


    public void setHealthCheck(HealthCheck healthCheck) {

        this.healthCheck = healthCheck;

    }


}
