package Controller.Relationship;

import Controller.Contact.ContactAddDateAndTimeViewController;
import Controller.Contact.ContactAddLocationViewController;
import DAO.UserDAOOracleImpl;
import Model.Contact;
import Model.Relationship;
import Model.User;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RelationshipAddSecondUserViewController implements Initializable {


    @FXML
    private TableView<User> tblUser;
    @FXML
    private TableColumn<User, String> colUserName;
    @FXML
    private TableColumn<User, String> colFirstName;
    @FXML
    private TableColumn<User, String> colLastName;
    @FXML
    private TableColumn<User, String> colGender;
    @FXML
    private TableColumn<User, LocalDate> colDateOfBirth;
    @FXML
    private TableColumn<User, LocalDate> colDateOfDeath;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnCancel;


    private ObservableList<User> userList = null;

    private User selectedUser = null;
    private Relationship relationship = null;


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


        relationship.setUser2(selectedUser);


            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Relationship/RelationshipAddTypeView.fxml"));
                Parent root = fxmlLoader.load();

                // Access controller
                RelationshipAddTypeViewController relationshipAddTypeViewController = fxmlLoader.getController();
                // Set contact
                relationshipAddTypeViewController.setRelationship(relationship);


                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Pick a location:");
                root.requestFocus();
                stage.setScene(new Scene(root));

            } catch (IOException e) {

                e.printStackTrace();

            }

    }


    @FXML
    void tblUserOnMouseClicked(MouseEvent event) {

        selectedUser = tblUser.getSelectionModel().getSelectedItem();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupUserTable();
        // Remove "firstUser" from the list
        updateUserTable();

    }


    public void setRelationship(Relationship relationship) {

        this.relationship = relationship;
        // Remove "firstUser" from the list
        userList.removeIf(user -> user.getId() == relationship.getUser1().getId());

    }


    private void setupUserTable() {

        // Setup the columns in the table
        colUserName.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        colGender.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dateOfBirth"));
        colDateOfDeath.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dateOfDeath"));

    }


    private void updateUserTable() {

        // Get all users through the UserDAOOracleImpl
        try {

            userList = new UserDAOOracleImpl().getAll();


        } catch (SQLException e) {

            e.printStackTrace();

        }

        // Set the ObservableList of users as the content of the table
        tblUser.setItems(userList);

    }


}