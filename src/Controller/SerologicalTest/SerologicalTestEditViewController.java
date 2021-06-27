package Controller.SerologicalTest;

import DAO.SerologicalTestDAOOracleImpl;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class SerologicalTestEditViewController implements Initializable {

    @FXML
    private ChoiceBox<String> cbIgM;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private DatePicker dpResultDate;
    @FXML
    private ChoiceBox<String> cbIgG;


    private SerologicalTest selectedSerologicalTest = null;
    private ObservableList<String> availableChoices = FXCollections.observableArrayList("positive", "negative");


    public SerologicalTestEditViewController(SerologicalTest selectedSerologicalTest) {

        this.selectedSerologicalTest = selectedSerologicalTest;

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

        if (validation()) { // validation() checks if all the required textfields are filled.

            // Fetch values from the view components.
            selectedSerologicalTest.setIgm(cbIgM.getValue());
            selectedSerologicalTest.setIgg(cbIgG.getValue());
            selectedSerologicalTest.setDateResult(dpResultDate.getValue());

            // Ask for the user to confirm changes.
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm changes");
            confirmAlert.setContentText("The serological test will be modified. Are you sure you want to proceed?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            // If user confirms.
            if (result.get() == ButtonType.OK) {
                // Then then the new serological test will be added on the db through the DAOOracle.
                try {

                    new SerologicalTestDAOOracleImpl().update(selectedSerologicalTest);

                    // Serological test successfully added. Show alert.
                    Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    errorAlert.setContentText("Serological test successfully modified.");
                    errorAlert.showAndWait();

                    // Close the stage.
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();


                } catch (SQLException e) { // Manage all SQL exeptions.

                    // CHECK date of birth trigger exception raised.
                    if (e.getErrorCode() == 20001) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("This serological test has a result date > date of birth.");
                        errorAlert.showAndWait();
                    }
                    // CHECK date of death trigger exception raised.
                    if (e.getErrorCode() == 20002) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("This serological test has a result date < date of birth.");
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

        if (cbIgM.getValue() == null) {
            errorMsg.append("- Please select IgM positivity.\n");
            isValid = false;
        }

        if (cbIgG.getValue() == null) {
            errorMsg.append("- Please select IgM positivity.\n");
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

        //
        cbIgM.setItems(availableChoices);
        cbIgG.setItems(availableChoices);

        cbIgM.setValue(selectedSerologicalTest.getIgm());
        cbIgG.setValue(selectedSerologicalTest.getIgg());
        dpResultDate.setValue(selectedSerologicalTest.getDateResult());

    }

}