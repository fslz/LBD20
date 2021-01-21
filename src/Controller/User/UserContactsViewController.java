package Controller.User;

import DAO.ContactDAOOracleImpl;
import DAO.UserDAOOracleImpl;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UserContactsViewController implements Initializable {

    private User selectedUser;
    private User contactUser;
    private Location contactLocation;

    private ObservableList<Contact> userContactsList;

    @FXML
    private TableView<Contact> tblUserContacts;
    @FXML
    private TableColumn<Contact, User> colUser;
    @FXML
    private TableColumn<Contact, String> colUserName;
    @FXML
    private TableColumn<Contact, String> colFirstName;
    @FXML
    private TableColumn<Contact, String> colLastName;
    @FXML
    private TableColumn<Contact, Location> colLocation;
    @FXML
    private TableColumn<Contact, String> colLocationName;
    @FXML
    private TableColumn<Contact, String> colLocationCity;
    @FXML
    private TableColumn<Contact, LocalDateTime> colDateReceived;
    @FXML
    private MenuItem cmContacts;
    @FXML
    private MenuItem cmRelationships;
    @FXML
    private MenuItem cmSwabs;
    @FXML
    private MenuItem cmSerologicalTests;
    @FXML
    private MenuItem cmHealthChecks;
    @FXML
    private Button btnToUsers;
    @FXML
    private Button btnAddUserContact;
    @FXML
    private Button btnEditUserContact;
    @FXML
    private Button btnDeleteUserContact;








    @FXML
    void btnAddUserContactOnAction(ActionEvent event) {

        // Add Contact

    }


    @FXML
    void btnDeleteUserContactOnAction(ActionEvent event) {

        // Delete Contact

    }


    @FXML
    void btnEditUserContactOnAction(ActionEvent event) {

        // Edit Contact?

    }


    // Back to Users List
    @FXML
    void btnToUsersOnAction(ActionEvent event) {

        try {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/User/UsersView.fxml"));
            Scene usersView = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            root.requestFocus();
            stage.setScene(usersView);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void tblUserContactsOnMouseClicked(MouseEvent event) {

    }


    public void setSelectedUser(User selectedUser) {

        this.selectedUser = selectedUser;

        this.userContactsList = (ObservableList<Contact>) selectedUser.getContactList();

        System.out.println(selectedUser + " user instance in UserContactsViewController");

        for(Contact contact : userContactsList){
            System.out.println(contact.getLocation().getCity());
        }


        tblUserContacts.setItems(userContactsList);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colUserName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Contact, String> c) {
                return new SimpleStringProperty(c.getValue().getUser2().getUserName());
            }
        });

        colFirstName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Contact, String> c) {
                return new SimpleStringProperty(c.getValue().getUser2().getFirstName());
            }
        });

        colLastName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Contact, String> c) {
                return new SimpleStringProperty(c.getValue().getUser2().getLastName());
            }
        });

        colLocationName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Contact, String> c) {
                return new SimpleStringProperty(c.getValue().getLocation().getName());
            }
        });

        colLocationCity.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Contact, String> c) {
                return new SimpleStringProperty(c.getValue().getLocation().getCity());
            }
        });

        // Setting up Date column
        colDateReceived.setCellValueFactory(new PropertyValueFactory<Contact, LocalDateTime>("dateReceived"));


        //setupUserTable();
        //updateUserTable();

    }


    private void updateUserTable() {


        try {

            userContactsList = new ContactDAOOracleImpl().getAllByUserId(selectedUser);


        } catch (SQLException e) {

            e.printStackTrace();

        }



        // Set the ObservableList of users as the content of the table
        tblUserContacts.setItems(userContactsList);

    }


    private void setupUserTable() {

        // Setup the columns in the table





    }


}
