package Controller;

import Model.User;
import Model.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class UserController implements Initializable {


    @FXML private Label lblFirstName;
    @FXML private Label lblLastName;
    @FXML private Label lblGender;
    @FXML private Label lblDateOfBirth;
    @FXML private Label lblDateOfDeath;
    @FXML private DatePicker dpDateOfBirth;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;
    @FXML private DatePicker dpDateOfDeath;
    @FXML private Button btnToMainMenu;
    @FXML private Button btnAdd;
    @FXML private Button btnModify;
    @FXML private Button btnDelete;
    @FXML private Button btnAddRelationship;
    @FXML private TableView<User> tblUser;
    @FXML private TableColumn<User, String> colFirstName;
    @FXML private TableColumn<User, String> colLastName;
    @FXML private TableColumn<User, String> colGender;
    @FXML private TableColumn<User, Date> colDateOfBirth;
    @FXML private TableColumn<User, Date> colDateOfDeath;

    ObservableList<User> userList;

    @FXML
    void btnToMainMenuOnAction(ActionEvent event) {

        try {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/MainMenuView.fxml"));
            Scene mainMenu = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainMenu);
            window.show();

        }

        catch (IOException e) {

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Setup the columns in the table
        colFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("First Name"));
        colLastName.setCellValueFactory(new PropertyValueFactory<User, String>("Last Name"));
        colGender.setCellValueFactory(new PropertyValueFactory<User, String>("Gender"));
        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<User, Date>("Date of birth"));
        colDateOfDeath.setCellValueFactory(new PropertyValueFactory<User, Date>("Date of death"));

        // Get all users through the UserDAO
        userList = new UserDAO().getUsersAll();
        // Set the list of users as the ObservableList of the table (the content of the table)
        tblUser.setItems(userList);

    }


}
