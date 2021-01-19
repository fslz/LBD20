package Controller.OldControllers;

import Model.Location;
import DAO.LocationDAOOracleImpl;
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

public class LocationAddViewController implements Initializable {

    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCity;
    @FXML
    private ChoiceBox<String> cbCategory;


    private ObservableList<String> availableChoices = FXCollections.observableArrayList("recreational", "entertainment", "religious", "education", "healthcare", "transportation", "business");


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
            confirmAlert.setContentText("The new location will be created. Are you sure you want to proceed?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            // If user confirms
            if (result.get() == ButtonType.OK) {
                // Then then the new location will be added on the db through the LocationDAOOracleImpl
                try {

                    new LocationDAOOracleImpl().create(
                            new Location(0,
                                    txtName.getText(),
                                    txtCity.getText(),
                                    cbCategory.getValue()
                            )
                    );

                    // User successfully added. Show alert.
                    Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    errorAlert.setContentText("Location successfully created.");
                    errorAlert.showAndWait();

                    // Close the UserInsertView stage
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();


                } catch (SQLException e) {

                    // UNIQUE constraint violation
                    if (e.getErrorCode() == 1) {    // TODO check constraint violation and error code
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("A location with this name already exists.");
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

        if (txtName.getText() == null || txtName.getText().trim().isEmpty()) {
            errorMsg.append("- Please enter a username.\n");
            isValid = false;
        }
        if (txtCity.getText() == null || txtCity.getText().trim().isEmpty()) {
            errorMsg.append("- Please enter a first name.\n");
            isValid = false;
        }
        if (cbCategory.getValue() == null) {
            errorMsg.append("- Please select gender.\n");
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

        cbCategory.setItems(availableChoices);

    }


}
