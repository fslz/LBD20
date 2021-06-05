package Controller.Location;

import Controller.User.UserContactsViewController;
import Controller.User.UserEditViewController;
import DAO.ContactDAOOracleImpl;
import Model.Location;
import DAO.LocationDAOOracleImpl;
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
    public void cmContactsOnAction(ActionEvent actionEvent) {

        try{

            selectedLocation.setContactList(new ContactDAOOracleImpl().getAllByLocationId(selectedLocation)); // Load all contacts of the selected User

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Location/LocationContactsView.fxml"));
            Parent root = fxmlLoader.load();

            LocationContactsViewController locationContactsViewController = fxmlLoader.getController();
            locationContactsViewController.setSelectedLocation(selectedLocation);

            Stage stage = (Stage) tblLocation.getScene().getWindow(); // getScene() is only available for components that inherit from Node. A MenuItem does not inherit from Node.

            Scene userContactsView = new Scene(root);
            stage.setTitle("Location Contacts");
            root.requestFocus();
            stage.setScene(userContactsView);

        }

        catch(IOException e){
            // TODO Handle exceptions
        }
        catch(SQLException e){
            // TODO Handle exceptions
        }

    }


    @FXML
    void btnToMainMenuOnAction(ActionEvent event) {

        try {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/MainMenuView.fxml"));
            Scene mainMenu = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            root.requestFocus();
            stage.setTitle("Main Menu");
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

            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Location/LocationEditView.fxml"));
                Parent root = fxmlLoader.load();

                LocationEditViewController locationEditViewController = fxmlLoader.getController();
                locationEditViewController.setSelectedLocation(selectedLocation);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Edit Location");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL); // Modify the modality of the stage to be modal (the user cannot interact with the calling stage)
                stage.showAndWait();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        else{

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select a location from the table");
            alert.setHeaderText("Location not selected");
            alert.showAndWait();

        }

        updateLocationTable();

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
            alert.setContentText("Please select a location from the table.");
            alert.setHeaderText("Location not selected");
            alert.showAndWait();

        }

        updateLocationTable();

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