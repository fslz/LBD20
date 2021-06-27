package Controller.HealthCheck;

import DAO.HealthCheckDAOOracleImpl;
import Model.HealthCheck;
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

public class HealthCheckEditViewController implements Initializable {

    //cbFever
           // cbSmellTasteDisorders
    @FXML
    private ChoiceBox<String> cbFever;
    @FXML
    private ChoiceBox<String> cbSmellTasteDisorders;
    @FXML
    private Label lblFever;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private DatePicker dpResultDate;
    @FXML
    private ChoiceBox<String> cbRespiratoryDisorders;
    @FXML
    private Label lblRespiratoryDisorders;
    @FXML
    private Label lblSmellTasteDisorders;


    private HealthCheck healthCheck = null;
    private ObservableList<String> availableChoices = FXCollections.observableArrayList("Y", "N");


    public HealthCheckEditViewController (HealthCheck selectedHealthCheck){

        this.healthCheck = selectedHealthCheck;

    }


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

            // Fetch values from the view components.
            healthCheck.setFever(cbFever.getValue());
            healthCheck.setRespiratoryDisorder(cbRespiratoryDisorders.getValue());
            healthCheck.setSmellTasteDisorder(cbSmellTasteDisorders.getValue());
            healthCheck.setDateResult(dpResultDate.getValue());

            // Ask for the user to confirm changes.
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm changes");
            confirmAlert.setContentText("The new health check will be updated. Are you sure you want to proceed?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            // If user confirms.
            if (result.get() == ButtonType.OK) {
                // Then then the new health check will be added on the db through the DAOOracle.
                try {

                    new HealthCheckDAOOracleImpl().update(healthCheck);

                    // Health check successfully added. Show alert.
                    Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    errorAlert.setContentText("Health Check test successfully updated.");
                    errorAlert.showAndWait();

                    // Close the UserInsertView stage
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();


                } catch (SQLException e) { // Manage all SQL exeptions.

                    // CHECK date of birth trigger exception raised.
                    if (e.getErrorCode() == 20001) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("This health check has a result date > date of birth.");
                        errorAlert.showAndWait();
                    }
                    // CHECK date of death trigger exception raised.
                    if (e.getErrorCode() == 20002) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("This health check has a result date < date of birth.");
                        errorAlert.showAndWait();
                    }

                }

            } else {
                // Else close the confirmation dialog.
                confirmAlert.close();

            }

        }

    }


    private boolean validation() {

        boolean isValid = true;
        StringBuilder errorMsg = new StringBuilder();

        if (dpResultDate.getValue() == null) {
            errorMsg.append("- Please select a valid date.\n");
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

        if(cbSmellTasteDisorders.getValue() == null) {
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
        cbSmellTasteDisorders.setItems(availableChoices);

        cbFever.setValue(healthCheck.getFever());
        cbRespiratoryDisorders.setValue((healthCheck.getRespiratoryDisorder()));
        cbSmellTasteDisorders.setValue(healthCheck.getSmellTasteDisorder());
        dpResultDate.setValue(healthCheck.getDateResult());

    }


    public void setHealthCheck(HealthCheck healthCheck) {

        this.healthCheck = healthCheck;

    }
}
