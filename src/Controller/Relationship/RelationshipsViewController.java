package Controller.Relationship;

import Controller.Contact.ContactAddSecondUserViewController;
import Controller.Relationship.RelationshipAddSecondUserViewController;
import Model.Contact;
import Model.Relationship;
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


public class RelationshipsViewController implements Initializable {

    @FXML
    private TableView<Relationship> tblUserRelationships;
    @FXML
    private TableColumn<Relationship, User> colUser;
    @FXML
    private TableColumn<Relationship, String> colUserName;
    @FXML
    private TableColumn<Relationship, String> colFirstName;
    @FXML
    private TableColumn<Relationship, String> colLastName;
    @FXML
    private TableColumn<Relationship, String> colRelationshipType;
    @FXML
    private Button btnToUsers;
    @FXML
    private Button btnAddUserRelationship;
    @FXML
    private Button btnEditUserRelationship;
    @FXML
    private Button btnDeleteUserRelationship;


    private User selectedUser = null;


    @FXML
    void btnAddUserRelationshipOnAction(ActionEvent event) {

        Relationship relationship = new Relationship();
        relationship.setUser1(selectedUser);

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Relationship/RelationshipAddSecondUserView.fxml"));
            Parent root = fxmlLoader.load();

            // Access controller
            RelationshipAddSecondUserViewController relationshipAddSecondUserViewController = fxmlLoader.getController();
            // Set contact
            relationshipAddSecondUserViewController.setRelationship(relationship);

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
    void btnDeleteUserRelationshipOnAction(ActionEvent event) {

    }

    @FXML
    void btnEditUserRelationshipOnAction(ActionEvent event) {

    }

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

        // Set the user instance
        this.selectedUser = selectedUser;

        // Assign the list of relationships of the user as the TableView content.
        tblUserRelationships.setItems((ObservableList<Relationship>)selectedUser.getRelationshipList());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupUserTable();
        //updateUserTable();

    }


    private void setupUserTable() {

        // Setup the columns in the table

        colUserName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Relationship, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Relationship, String> c) {
                return new SimpleStringProperty(c.getValue().getUser2().getUserName());
            }
        });


        colFirstName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Relationship, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Relationship, String> c) {
                return new SimpleStringProperty(c.getValue().getUser2().getFirstName());
            }
        });


        colLastName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Relationship, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Relationship, String> c) {
                return new SimpleStringProperty(c.getValue().getUser2().getLastName());
            }
        });

        colRelationshipType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Relationship, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Relationship, String> c) {
                return new SimpleStringProperty(c.getValue().getType());
            }
        });

    }
}
