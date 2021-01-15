package Controller.Location;

import Model.Location;
import Model.LocationDAOOracleImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class LocationsViewController implements Initializable {

    @FXML
    private TableView<Location> tblLocation;
    @FXML
    private TableColumn<Location, String> colName;
    @FXML
    private TableColumn<Location, String> colCity;
    @FXML
    private TableColumn<Location, String> colCategory;

    ObservableList<Location> locationList;

    Location selectedLocation = null;


    @FXML
    void btnToMainMenuOnAction(ActionEvent event) {

        try {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/MainMenuView.fxml"));
            Scene mainMenu = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            root.requestFocus();
            stage.setScene(mainMenu);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void btnAddLocationOnAction(ActionEvent event) {

        showAddLocationView();

        updateLocationTable();

    }



    @FXML
    void btnEditLocationOnAction(ActionEvent event) {

        if (selectedLocation != null) {

            showEditLocationView();

        }

        else{

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select a location from the table");
            alert.setHeaderText("Location not selected");
            alert.showAndWait();

        }

        updateLocationTable();

    }


    @FXML
    void btnDeleteLocationOnAction(ActionEvent event) {

        if (selectedLocation != null) {

            // Ask for the user to confirm changes
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm changes");
            confirmAlert.setHeaderText("");
            confirmAlert.setContentText("The location will be deleted. Do you want to proceed?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            if(result.get() == ButtonType.OK){

                try{

                    new LocationDAOOracleImpl().delete(selectedLocation);

                }
                catch(SQLException e){
                    System.out.println(e.getErrorCode());
                }

            }

        }
        else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select a location from the table");
            alert.setHeaderText("Location not selected");
            alert.showAndWait();

        }

        updateLocationTable();

    }


    private void showEditLocationView() {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Location/LocationEditView.fxml"));
            Parent root = fxmlLoader.load();

            LocationEditViewController LocationEditViewController = fxmlLoader.getController();
            LocationEditViewController.setSelectedLocation(selectedLocation);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Location");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL); // Modify the modality of the stage to be modal (cannot interact with the calling stage)
            stage.showAndWait();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }


    private void showAddLocationView(){

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Location/LocationAddView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Location");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        }

        catch(IOException e){

            e.printStackTrace();

        }

    }


    /**
     * Fired when the user clicks on the table (selects a row)
     */
    @FXML
    private void tblLocationOnMouseClicked(MouseEvent event) {

        selectedLocation = tblLocation.getSelectionModel().getSelectedItem();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupLocationTable();
        updateLocationTable();

    }


    private void updateLocationTable() {

        // Get all locations through the LocationDAOOracleImpl
        try {

            locationList = new LocationDAOOracleImpl().getAll();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        // Set the ObservableList of locations as the content of the table
        tblLocation.setItems(locationList);

    }

    private void setupLocationTable() {

        // Setup the columns in the table
        colName.setCellValueFactory(new PropertyValueFactory<Location, String>("name"));
        colCity.setCellValueFactory(new PropertyValueFactory<Location, String>("city"));
        colCategory.setCellValueFactory(new PropertyValueFactory<Location, String>("category"));

    }


}
