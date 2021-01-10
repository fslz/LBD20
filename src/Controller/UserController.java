package Controller;

import Model.User;
import Model.UserDAO;
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

import java.io.IOException;
import java.net.URL;
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


    ObservableList<User> userList;

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

        /*
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(txtFirstName.getText() + "\n" +
                txtLastName.getText() + "\n" +
                ((RadioButton) genderToggleGroup.getSelectedToggle()).getText() + "\n" +
                dpDateOfBirth.getValue() + "\n" +
                dpDateOfDeath.getValue());
        alert.showAndWait();
        */

        // TODO add email
        new UserDAO().addUser(
                new User(0, txtFirstName.getText(),
                        txtLastName.getText(),
                        ( (RadioButton) genderToggleGroup.getSelectedToggle() ).getText(),
                        dpDateOfBirth.getValue(),
                        dpDateOfDeath.getValue()
                )
        );

        updateUserTable();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        // TODO send to DAO the User instance of the selected row in the TableView
        updateUserTable();
    }

    @FXML
    void btnModifyOnAction(ActionEvent event) {

        // User user = new User(...); // TODO instantiate User with values from textfields
        UserDAO userDAO = new UserDAO();
        //userDAO.updateUser(user);
        updateUserTable();
    }

    /**
     * Fired when the user clicks on the table (selects a row)

     */
    @FXML
    void tblUserOnMouseClicked(MouseEvent event) {

        User user = tblUser.getSelectionModel().getSelectedItem();

        txtFirstName.setText(user.getFirstName());
        txtLastName.setText(user.getLastName());

        if (user.getGender().equals("M")) rbMale.setSelected(true);
        else rbFemale.setSelected(true);

        dpDateOfBirth.setValue(user.getDateOfBirth());
        dpDateOfDeath.setValue(user.getDateOfDeath());


        /*
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(user.getId() + "\n" +
                user.getFirstName() + "\n" +
                user.getLastName() + "\n" +
                user.getGender() + "\n" +
                user.getDateOfBirth() + "\n" +
                user.getDateOfDeath());
        alert.showAndWait();
        */

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupUserTable();
        updateUserTable();

    }

    public void updateUserTable() {

        // Get all users through the UserDAO
        userList = new UserDAO().getUsersAll();
        // Set the ObservableList of users as the content of the table
        tblUser.setItems(userList);

    }

    public void setupUserTable() {

        // Setup the columns in the table
        colFirstName.setCellValueFactory(new PropertyValueFactory<User, SimpleStringProperty>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<User, SimpleStringProperty>("lastName"));
        colGender.setCellValueFactory(new PropertyValueFactory<User, SimpleStringProperty>("gender"));
        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dateOfBirth"));
        colDateOfDeath.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dateOfDeath"));

    }

}
