package Controller.HealthCheck;

import DAO.HealthCheckDAOOracleImpl;
import Model.HealthCheck;
import Model.User;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class HealthChecksViewController implements Initializable {


    @FXML
    private TableView<HealthCheck> tblUserHealthCheck;
    @FXML
    private TableColumn<HealthCheck, String> colFever;
    @FXML
    private TableColumn<HealthCheck, String> colRespiratoryDisorder;
    @FXML
    private TableColumn<HealthCheck, String> colSmellTasteDisorder;
    @FXML
    private TableColumn<HealthCheck, LocalDateTime> colDateResult;


    private User selectedUser = null;
    private HealthCheck selectedHealthCheck = null;


    @FXML
    void btnAddUserHealthCheckOnAction(ActionEvent event) {

        HealthCheck healthCheck = new HealthCheck();
        healthCheck.setUser(selectedUser);

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/HealthCheck/HealthCheckEditView.fxml"));

            HealthCheckAddViewController healthCheckAddViewController = new HealthCheckAddViewController(healthCheck);

            fxmlLoader.setController(healthCheckAddViewController);

            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Enter health check test informations");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {

            e.printStackTrace();

        }

        updateHealthCheckTable();

    }


    @FXML
    void btnEditUserHealthCheckOnAction(ActionEvent event) {

        if (selectedHealthCheck != null) {

            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/HealthCheck/HealthCheckEditView.fxml"));

                // Instantiate the relative controller and assign the swab that needs to be edited.
                HealthCheckEditViewController healthCheckEditViewController = new HealthCheckEditViewController(selectedHealthCheck);

                // Assign the controller that needs to be associated with the chosen view.
                fxmlLoader.setController(healthCheckEditViewController);

                // This calls the Initialize() method of the controller.
                Parent root = fxmlLoader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Edit health check.");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL); // Modify the modality of the stage to be modal (the user cannot interact with the calling stage)
                stage.showAndWait();

            } catch (IOException e) {

                e.printStackTrace();

            }
            catch (Exception e) {

                e.printStackTrace();

            }

        }

        else{

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select a swab from the table.");
            alert.setHeaderText("Swab not selected.");
            alert.showAndWait();

        }

        updateHealthCheckTable();

    }


    @FXML
    void btnDeleteUserHealthCheckOnAction(ActionEvent event) {

        if (selectedHealthCheck != null) {

            // Ask for the user to confirm changes
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm changes");
            confirmAlert.setHeaderText("");
            confirmAlert.setContentText("The health check test will be deleted. Do you want to proceed?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            if(result.get() == ButtonType.OK){

                try{

                    new HealthCheckDAOOracleImpl().delete(selectedHealthCheck);

                }
                catch(SQLException e){
                    System.out.println(e.getErrorCode());
                }

            }

        }
        else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select a serological test from the table.");
            alert.setHeaderText("Serological test not selected.");
            alert.showAndWait();

        }

        updateHealthCheckTable();

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
    void tblUserHealthCheckOnMouseClicked(MouseEvent event) {

        this.selectedHealthCheck = tblUserHealthCheck.getSelectionModel().getSelectedItem();

    }


    public void setSelectedUser(User selectedUser) {

        // Set the user instance.
        this.selectedUser = selectedUser;

        // Set the ObservableList of users as the content of the table.
        tblUserHealthCheck.setItems((ObservableList<HealthCheck>) selectedUser.getHealthCheckList());

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupHealthChecksTable();

    }


    private void updateHealthCheckTable() {

        // Get all users through the HealthCheckDAOOracleImpl.
        try {

            selectedUser.setHealthCheckList(new HealthCheckDAOOracleImpl().getAllByUserId(selectedUser)) ;

            // Set the ObservableList of serological tests as the content of the table.
            tblUserHealthCheck.setItems((ObservableList<HealthCheck>) selectedUser.getHealthCheckList());

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }


    private void setupHealthChecksTable() {

        // Setup the columns in the table
        colFever.setCellValueFactory(new PropertyValueFactory<HealthCheck, String>("fever"));
        colRespiratoryDisorder.setCellValueFactory(new PropertyValueFactory<HealthCheck, String>("respiratoryDisorder"));
        colSmellTasteDisorder.setCellValueFactory(new PropertyValueFactory<HealthCheck, String>("smellTasteDisorder"));
        colDateResult.setCellValueFactory(new PropertyValueFactory<HealthCheck, LocalDateTime>("dateResult"));

    }

}
