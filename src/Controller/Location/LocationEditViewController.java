package Controller.Location;

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

public class LocationEditViewController implements Initializable {

    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCity;
    @FXML
    private ChoiceBox<String> cbCategory;


    private Location selectedLocation;

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
            confirmAlert.setHeaderText("");
            confirmAlert.setContentText("Apply changes?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            // If user confirms
            if (result.get() == ButtonType.OK) {
                // Then apply changes to the user on the db through the DAOOracle
                try {

                    new LocationDAOOracleImpl().update(
                            new Location(selectedLocation.getId(),
                                    txtName.getText(),
                                    txtCity.getText(),
                                    cbCategory.getValue()
                            )
                    );

                    // User successfully edited. Show alert.
                    Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    errorAlert.setContentText("Location successfully edited.");
                    errorAlert.showAndWait();

                    // Close the UserEditView stage
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();


                } catch (SQLException e) {

                    // UNIQUE constraint violation
                    if (e.getErrorCode() == 1) { // TODO check for violation and error code
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("A location with this name in this city already exists.");
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

    public void setSelectedLocation(Location selectedLocation) {

        this.selectedLocation = selectedLocation;

        setComponentsValues();

    }


    private void setComponentsValues() {

        txtName.setText(selectedLocation.getName());
        txtCity.setText(selectedLocation.getCity());
        cbCategory.setValue(selectedLocation.getCategory());

    }


    private boolean validation() {

        boolean isValid = true;
        StringBuilder errorMsg = new StringBuilder();

        if (txtName.getText() == null || txtName.getText().trim().isEmpty()) {
            errorMsg.append("- Please enter a location name.\n");
            isValid = false;
        }
        if (txtCity.getText() == null || txtCity.getText().trim().isEmpty()) {
            errorMsg.append("- Please enter a city.\n");
            isValid = false;
        }

        if (cbCategory.getValue() == null) {
            errorMsg.append("- Please select a category.\n");
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
