package Controller.SerologicalTest;

import DAO.SerologicalTestDAOOracleImpl;
import DAO.SwabDAOOracleImpl;
import Model.SerologicalTest;
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

public class SerologicalTestAddPropertiesViewController implements Initializable {


    @FXML
    private ChoiceBox<String> cbIgM;
    @FXML
    private Label lblIgM;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private DatePicker dpContactDate;
    @FXML
    private ChoiceBox<String> cbIgG;
    @FXML
    private Label lblIgG;


    private LocalDateTime localDateTime = null;
    private SerologicalTest serologicalTest = null;
    private ObservableList<String> availableChoices = FXCollections.observableArrayList("positive", "negative");


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

            serologicalTest.setIgm(cbIgM.getValue());
            serologicalTest.setIgg(cbIgG.getValue());


            LocalDate localDate = dpContactDate.getValue();
            serologicalTest.setDateResult(localDate);

            // Ask for the user to confirm changes
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm changes");
            confirmAlert.setContentText("The new serological test will be created. Are you sure you want to proceed?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            // If user confirms
            if (result.get() == ButtonType.OK) {
                // Then then the new contact will be added on the db through the DAOOracle
                try {

                    new SerologicalTestDAOOracleImpl().create(serologicalTest);

                    // Contact successfully added. Show alert.
                    Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    errorAlert.setContentText("Serological test successfully created.");
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

        if(cbIgM.getValue() == null) {
            errorMsg.append("- Please select IgM positivity.\n");
            isValid = false;
        }

        if(cbIgG.getValue() == null) {
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

        cbIgM.setItems(availableChoices);
        cbIgG.setItems(availableChoices);

    }

    public void setSerologicalTest(SerologicalTest serologicalTest) {

        this.serologicalTest = serologicalTest;

    }

}
