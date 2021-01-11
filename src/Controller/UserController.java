package Controller;

import Model.User;
import Model.UserDAO;
import com.sun.org.apache.xpath.internal.operations.String;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Stage;
import sun.java2d.pipe.SpanShapeRenderer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class UserController implements Initializable {


    @FXML
    private Label lblFirstName;
    @FXML
    private Label lblLastName;
    @FXML
    private Label lblGender;
    @FXML
    private Label lblDateOfBirth;
    @FXML
    private Label lblDateOfDeath;
    @FXML
    private DatePicker dpDateOfBirth;
    @FXML
    private RadioButton rbMale;
    @FXML
    private RadioButton rbFemale;
    @FXML
    private ToggleGroup genderToggleGroup;
    @FXML
    private DatePicker dpDateOfDeath;
    @FXML
    private Button btnToMainMenu;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAddRelationship;
    @FXML
    private TableView<User> tblUser;
    @FXML
    private TableColumn<User, SimpleStringProperty> colFirstName;
    @FXML
    private TableColumn<User, SimpleStringProperty> colLastName;
    @FXML
    private TableColumn<User, SimpleStringProperty> colGender;
    @FXML
    private TableColumn<User, LocalDate> colDateOfBirth;
    @FXML
    private TableColumn<User, LocalDate> colDateOfDeath;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtUserName;
    @FXML
    private Label lblUserName;
    @FXML
    private TableColumn<User, SimpleStringProperty> colUserName;

    private ObservableList<User> userList;

    private User selectedUser = null;

    @FXML
    void btnToMainMenuOnAction(ActionEvent event) {

        try {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/MainMenuView.fxml"));
            Scene mainMenu = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainMenu);
            root.requestFocus();
            window.show();

        } catch (IOException e) {

        }

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        if(validation()){ // validation() checks if all the required textfields are filled
            try {
                new UserDAO().addUser(
                        new User(0,
                                txtUserName.getText(),
                                txtFirstName.getText(),
                                txtLastName.getText(),
                                ((RadioButton) genderToggleGroup.getSelectedToggle()).getText(),
                                dpDateOfBirth.getValue(),
                                dpDateOfDeath.getValue()
                        )
                );
            }
            catch(SQLException e){

                if(e.getSQLState().equals("23000")){ // UNIQUE username constraint violation.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("A user with this username already exists.");
                    alert.showAndWait();
                }

            }
        }


        updateUserTable();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        if (selectedUser != null) {
            new UserDAO().deleteUser(selectedUser);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a user row from the table");
            alert.showAndWait();
        }

        updateUserTable();

    }

    @FXML
    void btnModifyOnAction(ActionEvent event) {

        // User user = new User(...); // TODO instantiate User with values from textfields
        if (selectedUser != null) {
            //
        }
        //userDAO.updateUser(user);
        updateUserTable();
    }

    /**
     * Fired when the user clicks on the table (selects a row)
     */
    @FXML
    void tblUserOnMouseClicked(MouseEvent event) {

        selectedUser = tblUser.getSelectionModel().getSelectedItem();

        txtUserName.setText(selectedUser.getUserName());
        txtFirstName.setText(selectedUser.getFirstName());
        txtLastName.setText(selectedUser.getLastName());

        if (selectedUser.getGender().equals("M")) rbMale.setSelected(true);
        else rbFemale.setSelected(true);

        dpDateOfBirth.setValue(selectedUser.getDateOfBirth());
        dpDateOfDeath.setValue(selectedUser.getDateOfDeath());


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupUserTable();
        updateUserTable();

    }

    public void updateUserTable() {

        // Get all users through the UserDAO
        userList = new UserDAO().getUsersAll();
        selectedUser = null;
        // Set the ObservableList of users as the content of the table
        tblUser.setItems(userList);
        clearAll();

    }

    public void clearAll() {
        txtUserName.setText(null);
        txtFirstName.setText(null);
        txtLastName.setText(null);
        rbMale.setSelected(false);
        rbFemale.setSelected(false);
        dpDateOfBirth.setValue(null);
        dpDateOfDeath.setValue(null);
    }

    public void setupUserTable() {

        // Setup the columns in the table
        colUserName.setCellValueFactory(new PropertyValueFactory<User, SimpleStringProperty>("userName"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<User, SimpleStringProperty>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<User, SimpleStringProperty>("lastName"));
        colGender.setCellValueFactory(new PropertyValueFactory<User, SimpleStringProperty>("gender"));
        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dateOfBirth"));
        colDateOfDeath.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dateOfDeath"));

    }


    private boolean validation(){

        boolean isValid = true;
        StringBuilder errorMsg = new StringBuilder();

        if (txtUserName.getText() == null || txtUserName.getText().trim().isEmpty()) {
            errorMsg.append("- Please enter a username.\n");
            isValid = false;
        }
        if (txtFirstName.getText() == null || txtFirstName.getText().trim().isEmpty()) {
            errorMsg.append("- Please enter a first name.\n");
            isValid = false;
        }
        if (txtLastName.getText() == null || txtLastName.getText().trim().isEmpty()) {
            errorMsg.append("- Please enter a last name.\n");
            isValid = false;
        }
        if ( genderToggleGroup.getSelectedToggle() == null ) {
            errorMsg.append("- Please select a gender.\n");
            isValid = false;
        }
        if (dpDateOfBirth.getValue() == null) {
            errorMsg.append("- Please enter the date of birth.\n");
            isValid = false;
        }

        if(!isValid){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorMsg.toString());
            alert.showAndWait();
        }

        return isValid;

    }


}
