package Controller.Contact;

import DAO.LocationDAOOracleImpl;
import Model.Contact;
import Model.Location;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContactAddLocationViewController implements Initializable {


    private Contact contact = null;

    private Location selectedLocation = null;

    ObservableList<Location> locationList;


    @FXML
    private TableView<Location> tblLocation;
    @FXML
    private TableColumn<Location, String> colName;
    @FXML
    private TableColumn<Location, String> colCity;
    @FXML
    private TableColumn<Location, String> colCategory;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnCancel;



    @FXML
    void btnCancelOnAction(ActionEvent event) {

        contact = null;
        // Get the stage the button pressed belongs to
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // Close it
        stage.close();

    }

    @FXML
    void btnNextOnAction(ActionEvent event) {

        contact.setLocation(selectedLocation);

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Contact/ContactAddDateAndTimeView.fxml"));
            Parent root = fxmlLoader.load();

            // Access controller
            ContactAddDateAndTimeViewController contactAddDateAndTimeViewController = fxmlLoader.getController();
            // Set contact
            contactAddDateAndTimeViewController.setContact(contact);


            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Pick date and time:");
            root.requestFocus();
            stage.setScene(new Scene(root));

        }

        catch(IOException e){

            e.printStackTrace();

        }

    }


    public void setContact(Contact contact) {

        this.contact = contact;
        // Remove "firstUser" from the list

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
