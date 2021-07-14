package Controller.Location;

import Controller.Contact.ContactAddFirstUserViewController;
import Controller.Contact.ContactAddSecondUserViewController;
import Model.Contact;
import Model.Location;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class LocationContactsViewController implements Initializable {

    @FXML
    private TableView<Contact> tblLocationContacts;
    @FXML
    private TableColumn<Contact, User> colUser1;
    @FXML
    private TableColumn<Contact, String> colUser1UserName;
    @FXML
    private TableColumn<Contact, String> colUser1FirstName;
    @FXML
    private TableColumn<Contact, String> colUser1LastName;
    @FXML
    private TableColumn<Contact, User> colUser2;
    @FXML
    private TableColumn<Contact, String> colUser2UserName;
    @FXML
    private TableColumn<Contact, String> colUser2FirstName;
    @FXML
    private TableColumn<Contact, String> colUser2LastName;
    @FXML
    private TableColumn<Contact, LocalDateTime> colDateReceived;
    @FXML
    private Button btnAddLocationContact;
    @FXML
    private Button btnDeleteLocationContact;
    @FXML
    private Button btnToLocations;

    private Location selectedLocation = null;

    @FXML
    void btnToLocationsOnAction(ActionEvent event) {

        try {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/Location/LocationsView.fxml"));
            Scene usersView = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            root.requestFocus();
            stage.setScene(usersView);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnAddContactOnAction(ActionEvent event) {

        Contact contact = new Contact();
        contact.setLocation(selectedLocation);

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Contact/ContactAddFirstUserView.fxml"));
            Parent root = fxmlLoader.load();

            // Access controller
            ContactAddFirstUserViewController contactAddFirstUserViewController = fxmlLoader.getController();
            // Set contact
            contactAddFirstUserViewController.setContact(contact);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Pick a user");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        }

        catch(IOException e){

            e.printStackTrace();

        }

    }

    @FXML
    void btnDeleteContactOnAction(ActionEvent event) {

    }

    @FXML
    void btnEditContactOnAction(ActionEvent event) {

    }

    @FXML
    void tblContactsOnMouseClicked(MouseEvent event) {

    }


    public void setSelectedLocation(Location selectedLocation) {

        // Set the location instance
        this.selectedLocation = selectedLocation;


        // Assign the list of contacts of the location as the TableView content.
        tblLocationContacts.setItems((ObservableList<Contact>)selectedLocation.getContactList());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupUserTable();

    }


    private void setupUserTable() {
        // Setup the columns in the table

        colUser1UserName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Contact, String> c) {
                return new SimpleStringProperty(c.getValue().getUser1().getUserName());
            }
        });


        colUser1FirstName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Contact, String> c) {
                return new SimpleStringProperty(c.getValue().getUser1().getFirstName());
            }
        });


        colUser1LastName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Contact, String> c) {
                return new SimpleStringProperty(c.getValue().getUser1().getLastName());
            }
        });

        colUser2UserName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Contact, String> c) {
                return new SimpleStringProperty(c.getValue().getUser2().getUserName());
            }
        });

        colUser2FirstName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Contact, String> c) {
                return new SimpleStringProperty(c.getValue().getUser2().getFirstName());
            }
        });

        colUser2LastName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Contact, String> c) {
                return new SimpleStringProperty(c.getValue().getUser2().getLastName());
            }
        });

        // Setting up Date column
        colDateReceived.setCellValueFactory(new PropertyValueFactory<Contact, LocalDateTime>("dateReceived"));

    }
}
