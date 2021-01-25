package Controller.Relationship;

import DAO.ContactDAOOracleImpl;
import DAO.RelationshipDAOOracleImpl;
import Model.Relationship;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class RelationshipAddTypeViewController implements Initializable {


    @FXML
    private Button btnCancel;
    @FXML
    private ChoiceBox<String> cbRelationshipType;


    private Relationship relationship = null;

    private ObservableList<String> availableChoices = FXCollections.observableArrayList("work", "family");

    @FXML
    void btnCancelOnAction(ActionEvent event) {

        relationship = null;
        // Get the stage the button pressed belongs to
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // Close it
        stage.close();

    }


    @FXML
    void btnNextOnAction(ActionEvent event) {


        if (validation()) { // validation() checks if all the required textfields are filled

            relationship.setType(cbRelationshipType.getValue());  // TODO

            // Ask for the user to confirm changes
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm changes");
            confirmAlert.setContentText("The new relationship will be created. Are you sure you want to proceed?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            // If user confirms
            if (result.get() == ButtonType.OK) {
                // Then then the new relationship will be added on the db through the DAOOracle
                try {

                    new RelationshipDAOOracleImpl().create(relationship);

                    // Contact successfully added. Show alert.
                    Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    errorAlert.setContentText("Relationship successfully created.");
                    errorAlert.showAndWait();

                    // Close the stage
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();


                } catch (SQLException e) {

                    // CHECK user is already a member of the relationship
                    if (e.getErrorCode() == 20001) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("A relationship of this type between the selected users already exists.");
                        errorAlert.showAndWait();
                    }

                }

            } else {
                // Else close the confirmation dialog
                confirmAlert.close();

            }

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cbRelationshipType.setItems(availableChoices);

    }


    private boolean validation() {

        boolean isValid = true;
        StringBuilder errorMsg = new StringBuilder();

        if (cbRelationshipType.getValue() == null) {
            errorMsg.append("- Please select a valid type.\n");
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


    public void setRelationship(Relationship relationship) {

        this.relationship = relationship;

    }


}
